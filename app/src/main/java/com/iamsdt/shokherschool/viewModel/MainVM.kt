package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import com.iamsdt.shokherschool.database.MyDatabase
import com.iamsdt.shokherschool.database.table.AuthorTable
import com.iamsdt.shokherschool.database.table.MediaTable
import com.iamsdt.shokherschool.database.table.PostTable
import com.iamsdt.shokherschool.model.PostModel
import com.iamsdt.shokherschool.retrofit.RetrofitData
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.utilities.MyDateUtil
import com.iamsdt.shokherschool.utilities.MyDateUtil.Companion.getReadableDate
import com.iamsdt.shokherschool.utilities.Utility
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

    private var allPost: MutableLiveData<List<MainPostModelClass>>? = null

    private var myDatabase: MyDatabase? = null

    private var authorIdArray = ArrayList<Int>()
    private var mediaIdArray = ArrayList<Int>()

    private var wpResponse:WPRestInterface ?= null

    private var dateList = ArrayList<String>()
    private var dateCheckedList = ArrayList<String>()

    init {
        myDatabase = MyDatabase.getInstance(application.baseContext)
        wpResponse = RetrofitData().wpRestInterface

        checkForData()
    }

    private fun checkForData(){
        val ser = Executors.newSingleThreadExecutor()
        ser.submit({
            val data = myDatabase?.postTableDao?.getAllDataList
            if (data == null || data.isEmpty()) {
                addRemoteData()
            }
        })
    }


    fun getAllPostList(): MutableLiveData<List<MainPostModelClass>>?{

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
                Utility.logger(message = "post data failed", throwable = t)
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
                            myDatabase!!.postTableDao.insert(table)
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
        Utility.logger("date saved start: $spSave")
    }

    private fun fillData() {

        if (allPost == null){
            allPost = MutableLiveData()
        }

        AsyncTask.execute({

            val arrayList = ArrayList<MainPostModelClass>()

            val postData = myDatabase!!.postTableDao.getAllDataList

            if (!postData.isEmpty()){

                for (post in postData) {

                    val authorName = myDatabase?.authorTableDao?.
                            getAuthorName(post.author!!)

                    val mediaLink = myDatabase?.mediaTableDao?.
                            getMediaThumbnail(post.featuredMedia!!)

                    val newModel = MainPostModelClass(
                            post.id,
                            getReadableDate(post.date!!),
                            post.title,
                            authorName,
                            mediaLink)

                    arrayList.add(newModel)
                }

                //put the data
                allPost!!.postValue(arrayList)
            }
        })
    }

    private fun saveMediaAndAuthor() {

        if (mediaIdArray.isEmpty() && authorIdArray.isEmpty()){
            return

        } else if (!mediaIdArray.isEmpty()){

            val ids = myDatabase?.mediaTableDao?.getMediaIds()

            for (media in mediaIdArray) {
                //fixme 12/8/2017 this create a  problem that is it will never update later

                if (ids!!.contains(media))continue

                val response = wpResponse?.getMediaByID(media)?.execute()

                if (!response!!.isSuccessful){
                    continue
                }

                val mediaResponse = response.body()
                val mediaDetails = mediaResponse?.mediaDetails?.sizes
                val mediaTable = MediaTable(mediaResponse?.id,
                        mediaResponse?.title?.rendered,
                        mediaDetails?.thumbnail?.sourceUrl,
                        mediaDetails?.medium?.sourceUrl,
                        mediaDetails?.full?.sourceUrl)

                myDatabase?.mediaTableDao?.insert(mediaTable)
            }

        } else if (!authorIdArray.isEmpty()){

            val ids = myDatabase?.authorTableDao?.getAuthorIds()

            for (author in authorIdArray) {
                //fixme 12/8/2017 this create a  problem that is it will never update later
                if (ids!!.contains(author)) {
                    continue
                }

                val response = wpResponse?.getAuthorByID(author)?.execute()

                if (!response!!.isSuccessful){
                    continue
                }

                val authorResponse = response.body()
                val authorTable = AuthorTable(
                        authorResponse?.avatarUrls?.avatar24,
                        authorResponse?.avatarUrls?.avatar48,
                        authorResponse?.avatarUrls?.avatar96,
                        authorResponse?.name,authorResponse?.link,
                        authorResponse?.description,authorResponse?.id)

                myDatabase?.authorTableDao?.insert(authorTable)
            }
        }
    }

    fun requestNewPost(date: String) {

        val call = wpResponse?.getFilterPostList(date)

        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Utility.logger(message = "post data failed", throwable = t)
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
                            myDatabase!!.postTableDao.insert(table)
                        }

                        saveMediaAndAuthor()

                        //now data saved
                        val arrayList = ArrayList<MainPostModelClass>()

                        val postDataFromDatabase = myDatabase!!.postTableDao.getAllDataList

                        if (!postDataFromDatabase.isEmpty()) {

                            for (post in postDataFromDatabase) {

                                val authorName = myDatabase?.authorTableDao?.
                                        getAuthorName(post.author!!)

                                val mediaLink = myDatabase?.mediaTableDao?.
                                        getMediaThumbnail(post.featuredMedia!!)

                                val newModel = MainPostModelClass(
                                        post.id,
                                        getReadableDate(post.date!!),
                                        post.title,
                                        authorName,
                                        mediaLink)

                                arrayList.add(newModel)
                            }

                            //put the data
                            allPost!!.postValue(arrayList)
                        }
                    })


                }

            }
        })
    }
}