package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import com.iamsdt.shokherschool.database.table.PostTable
import com.iamsdt.shokherschool.model.PostModel
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.utilities.MyDateUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 10:59 PM
 */
class MainVM(application: Application) : AndroidViewModel(application) {

    private var allPost: MutableLiveData<List<PostModel>>? = null

    private var authorIdArray = ArrayList<Int>()
    private var mediaIdArray = ArrayList<Int>()

    private var wpResponse:WPRestInterface ?= null

    private var dateList = ArrayList<String>()
    private var dateCheckedList = ArrayList<String>()

    private fun checkForData(){
        val services = Executors.newSingleThreadExecutor()
        services.submit({
            //TODO check data if not exist start services
        })
    }


    fun getAllPostList(): MutableLiveData<List<PostModel>>?{

        if (allPost == null){
            fillData()
        }

        return allPost
    }

    private fun addRemoteData() {
        val postResponse = wpResponse?.getAllPostList()
        remoteToDatabase(postResponse!!)
    }

    private fun remoteToDatabase(call: Call<List<PostResponse>>) {

        call.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Timber.e(t,"post data failed")
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                if (response!!.isSuccessful){

                    AsyncTask.execute({

                        val postData = response.body()

                        for (post in postData!!) {
                            val id = post.id
                            val title = post.title?.rendered
                            val date = post.date

                            if (!dateList.contains(date)){
                                dateList.add(date)
                            }

                            val author = post.author

                            if (!authorIdArray.contains(author)){
                                authorIdArray.add(author)
                            }

                            val media = post.featuredMedia

                            if (!mediaIdArray.contains(media)){
                                mediaIdArray.add(media)
                            }

                            val table = PostTable(id, date, author, title, media)

                            //insert data

                        }

                        saveMediaAndAuthor()

                        //now data saved
                        fillData()
                    })
                }

            }

        })
    }

    fun saveDate(context:Context){

        val pattern = "yyyy-MM-dd'T'HH:mm:ss"
        val dtf = SimpleDateFormat(pattern, Locale.getDefault())

        //current date and time
        var today: Date = dtf.parse(dtf.format(Date()))

        for (n in dateList) {
            if (dateCheckedList.contains(n)) return
            val date2 = dtf.parse(n)
            today = MyDateUtil.compareTwoDate(today, date2)
            dateCheckedList.add(n)
        }
        val spSave = dtf.format(today)
        MyDateUtil.setDateOnSp(context, spSave)
        Timber.i("date saved start: $spSave")
    }

    private fun fillData() {

        if (allPost == null){
            allPost = MutableLiveData()
        }

        AsyncTask.execute({
            val arrayList = ArrayList<PostModel>()
            //put the data

            //todo 12/30/2017 fill logic

            allPost!!.postValue(arrayList)
        })
    }

    private fun saveMediaAndAuthor() {

    }

    fun requestNewPost(date: String) {

        val call = wpResponse?.getFilterPostList(date)

        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Timber.e(t,"post data failed to load in request new post")
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                if (response!!.isSuccessful) {

                    AsyncTask.execute({

                        val postData = response.body()

                        for (post in postData!!) {
                            val id = post.id
                            val title = post.title?.rendered
                            val date2: String = post.date

                            if (!dateList.contains(date)) {
                                dateList.add(date)
                            }

                            val author = post.author

                            if (!authorIdArray.contains(author)) {
                                authorIdArray.add(author)
                            }

                            val media = post.featuredMedia

                            if (!mediaIdArray.contains(media)) {
                                mediaIdArray.add(media)
                            }

                            val table = PostTable(id, date2, author, title, media)

                            //insert data
                            //myDatabase!!.postTableDao.insert(table)
                        }

                        saveMediaAndAuthor()

                        //now data saved
                        val arrayList = ArrayList<PostModel>()

                        //todo add implement logic

                            //put the data
                            allPost!!.postValue(arrayList)
                    })


                }

            }
        })
    }
}