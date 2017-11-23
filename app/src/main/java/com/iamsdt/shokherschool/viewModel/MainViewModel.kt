package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import com.iamsdt.shokherschool.retrofit.DataResponse
import com.iamsdt.shokherschool.retrofit.RetrofitData
import com.iamsdt.shokherschool.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.retrofit.RetrofitLiveData
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.utilities.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

/**
* Created by Shudipto Trafder on 11/17/2017.
*/

class MainViewModel(application: Application):AndroidViewModel(application){

    private var allPost: RetrofitLiveData<List<PostResponse>> ?= null
    private var dataResponse:DataResponse ?= null

    init {
        val retrofit = RetrofitData()
        dataResponse = retrofit.dataResponse
    }

    fun getPostData(context: Context):RetrofitLiveData<List<PostResponse>>?{
        if (allPost == null){
            allPost = RetrofitHandler(dataResponse!!).getAllPostData()
        }

        saveDate(context,allPost!!)

        return allPost
    }

    fun requestNewPost(date:String){
        //RequestNewData(date,dataResponse!!,allPost!!).execute()

        val service = Executors.newSingleThreadExecutor()
        service.submit({
            // on background thread, obtain a fresh list of users
            val newData = dataResponse?.getSegmentedPost(date)

            newData?.enqueue(object :Callback<List<PostResponse>>{
                override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                    Utility.logger(message = "Update failed",throwable = t)
                }

                override fun onResponse(call: Call<List<PostResponse>>?,
                                        response: Response<List<PostResponse>>?) {

                    if (response!!.isSuccessful){
                        val arrayList = joinData(allPost!!,response.body()!!)
                        allPost?.postValue(arrayList)
                        Utility.logger("successful",allPost?.value?.size.toString())
                    }
                }

            })

        })

    }

    fun joinData(oldPost:RetrofitLiveData<List<PostResponse>>,
                 newData: List<PostResponse>):ArrayList<PostResponse>{

        val list:ArrayList<PostResponse> = ArrayList()

        for (n in oldPost.value!!.listIterator()){
            list.add(n)
        }

        for (i in newData){
            //remove possible duplication
            if (!list.contains(i)){
                list.add(i)
            }
        }

        print(list.size)

        return list
    }

    private fun saveDate(context: Context, allPost: RetrofitLiveData<List<PostResponse>>){

        val pattern = "yyyy-MM-dd'T'HH:mm:ssZ"
        val dtf = SimpleDateFormat(pattern)
        val today =Date()
        val simpleDate:String = dtf.format(today)

        //todo fix this
        var date1:Date = dtf.parse(simpleDate) as Date

        for (n in allPost.value!!.listIterator()){
            val date2 = dtf.parse(n.date)
             date1 = compareTwoDate(date1,date2)
        }

        Utility.setDateOnSp(context,date1.toString())
    }

    private fun compareTwoDate(first:Date, second:Date):Date
            = if (first.before(second)) { first} else{second}

}