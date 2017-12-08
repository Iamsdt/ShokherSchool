package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import com.iamsdt.shokherschool.retrofit.RetrofitData
import com.iamsdt.shokherschool.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.retrofit.RetrofitLiveData
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.utilities.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

/**
* Created by Shudipto Trafder on 11/17/2017.
*/

class MainViewModel(application: Application):AndroidViewModel(application){

    private var allPost: RetrofitLiveData<List<PostResponse>> ?= null
    private var wpRestInterface: WPRestInterface?= null

    //to remove possible re check again and again
    // when all post size is 20
    // the it will check 20 date
    // but 10 date is already checked
    //that's why i save the checked date
    // when viewmodel created it will create
    private var dateChecked:ArrayList<String> ?= null

    init {
        val retrofit = RetrofitData()
        wpRestInterface = retrofit.wpRestInterface
        dateChecked = ArrayList()
    }

    /**Get all post data
     * if post data is null
     * then make a server request
     * @return live data list*/
    fun getPostData():RetrofitLiveData<List<PostResponse>>?{
        if (allPost == null){
            allPost = RetrofitHandler(wpRestInterface!!).getAllPostData()
        }

        return allPost
    }

    override fun onCleared() {
        super.onCleared()
        allPost!!.cancel()
    }

    /**Get new post data according to date
     *
     * after successful put the new data to
     * Mutable Live Data
     *
     * @param date for querying data*/
    fun requestNewPost(date:String){

        Utility.logger("Get post date: $date")

        //don't put data to Mutable Live data
        //in main thread
        val service = Executors.newSingleThreadExecutor()
        service.submit({
            // on background thread, obtain a fresh list of users
            val newData = wpRestInterface?.getFilterPostList(date)

            //for debug
            Utility.logger("Url ${newData!!.request().url()}")

            newData.enqueue(object :Callback<List<PostResponse>>{
                override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                    Utility.logger(message = "Update failed",throwable = t)
                }

                override fun onResponse(call: Call<List<PostResponse>>?,
                                        response: Response<List<PostResponse>>?) {

                    if (response!!.isSuccessful){
                        val arrayList = joinData(allPost!!,response.body()!!)
                        allPost?.postValue(arrayList)
                        Utility.logger("successful on new date request",allPost?.value?.size.toString())
                    }
                }

            })

        })

    }

    /**
     * when i get some new data from server then need to
     * join list with previous one
     * because i don't replace the data
     * @param oldPost old data
     * @param newData new data
     *
     * @return a array list for both data with old + new
     * (first old then new data)*/
    fun joinData(oldPost:RetrofitLiveData<List<PostResponse>>,
                 newData: List<PostResponse>):ArrayList<PostResponse>{

        //return the joined data
        val list:ArrayList<PostResponse> = ArrayList()

        //first add the old data
        for (n in oldPost.value!!.listIterator()){
            list.add(n)
        }

        //same as previous
//        oldPost.value!!.listIterator().forEach {
//            list.add(it)
//        }

        //second add the new data
        newData
                .filterNot { //remove possible duplication
                    //some times old data contain new data(for duplicate call)
                    // check new data is already exist or not
                    list.contains(it)
                }
                .forEach { list.add(it) }

        Utility.logger("data join end")

        return list
    }

    /**
     * Save date to sp
     * first i get all the date data from viewmodel
     * then compare first date data with current date
     * to find which one is older
     * the the older date in sp
     *
     * @param context application context to access ap
     * @param allPost list of data
     * */

    fun saveDate(context: Context, allPost: List<PostResponse>){

        val pattern = "yyyy-MM-dd'T'HH:mm:ss"
        val dtf = SimpleDateFormat(pattern,Locale.getDefault())

        //current date and time
        val today:Date = dtf.parse(dtf.format(Date()))

        var compareDate = today
        for (n in allPost){

            //check date is already checked or not
            if (dateChecked!!.contains(n.date)){
                continue
            }

            val date2 = dtf.parse(n.date)
            val date3 = compareTwoDate(today,date2)

            //for debug
            Utility.logger("Compare date: $compareDate and $date2 -> result:$date3")
            compareDate = date3

            //save the checked date
            dateChecked?.add(n.date)
        }

        //if all data is checked then loop is not work
        // so it saving today date
        // that's we we gate date from arraylist
        val actualDate:Date?

        actualDate = when {
            today.before(compareDate) -> today
            compareDate.before(today) -> compareDate
            else -> //if loop not work then two date will be same
                //for this case get data from array list last position
                null
        }

        if (actualDate != null){
            val spSave = dtf.format(actualDate)
            Utility.setDateOnSp(context,spSave)
            Utility.logger("date saved start: $spSave")
        } else{
            //for debug
            val spSave = dateChecked!![dateChecked!!.size - 1]
            Utility.setDateOnSp(context,spSave)
            Utility.logger("date saved start: $spSave")
        }

        //save data to sp

    }

    /**
     * This methods for compare two date
     * to find out which date is older
     * @param first is consider as a first date
     * @param second is consider as a second date
     * @return the date older date
     * */
    private fun compareTwoDate(first:Date, second:Date):Date
            = if (first.before(second)) { first} else{second}
}