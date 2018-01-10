package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.table.AuthorTable
import com.iamsdt.shokherschool.data.database.table.MediaTable
import com.iamsdt.shokherschool.data.database.table.PostTable
import com.iamsdt.shokherschool.data.model.PostModel
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.data.utilities.MyDateUtil
import com.iamsdt.shokherschool.ui.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 10:59 PM
 */
class MainVM(application: Application) : AndroidViewModel(application) {

    private var allPost: MutableLiveData<List<PostModel>>? = null

    private val authorInserted = ArrayList<Int>()

    private var dateList = ArrayList<String>()
    private var dateCheckedList = ArrayList<String>()

    private var postTableDao: PostTableDao? = null
    private var authorTableDao: AuthorTableDao? = null
    private var wpRestInterface: WPRestInterface? = null

    fun setup(postTableDao: PostTableDao,
              authorTableDao: AuthorTableDao,
              wpRestInterface: WPRestInterface) {

        this.postTableDao = postTableDao
        this.authorTableDao = authorTableDao
        this.wpRestInterface = wpRestInterface

        Timber.i("setup method called")

    }

    fun getAllPostList(): MutableLiveData<List<PostModel>>? {

        if (allPost == null) {
            allPost = MutableLiveData()

            val runnable = Runnable {
                fillData()
            }

            Thread(runnable).start()
        }

        return allPost
    }

    private fun addRemoteData(callback: Call<List<PostResponse>>?) {

        Timber.i("call start for Remote data")

        var call = callback

        //if call is null make default request
        if (call == null) {
            //make request to database
            call = wpRestInterface?.getAllPostList()
        }

        call?.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Timber.e(t, "post data failed")
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                if (response!!.isSuccessful) {

                    Timber.i("Response come from server")

                    AsyncTask.execute({

                        val postData = response.body()

                        for (post in postData!!) {
                            val id = post.id
                            val title = post.title?.rendered
                            val content = post.content?.rendered
                            val date = post.date

                            //add date to date list
                            if (dateList.contains(date)) {
                                dateList.add(date)
                            }

                            //author id
                            val author = post.author
                            if (!authorInserted.contains(author)) {
                                //request data from server
                                val authorResponse = wpRestInterface?.getAuthorByID(author)?.execute()

                                if (authorResponse!!.isSuccessful) {
                                    val authorData = authorResponse.body()
                                    val authorTable = AuthorTable(
                                            authorData?.avatarUrls?.avatar24,
                                            authorData?.avatarUrls?.avatar48,
                                            authorData?.avatarUrls?.avatar96,
                                            authorData?.name, authorData?.link,
                                            authorData?.description, authorData?.id)

                                    authorTableDao?.insert(authorTable)

                                    authorInserted.add(author)
                                }
                            }

                            val media = post.featuredMedia
                            var mediaTable:MediaTable ?= null

                            if (media != 0){

                                val mediaResponse = wpRestInterface?.getMediaByID(media)?.execute()

                                if (mediaResponse!!.isSuccessful) {
                                    val mediaData = mediaResponse.body()
                                    //media image size
                                    val mediaDetails = mediaData?.mediaDetails?.sizes

                                    mediaTable = MediaTable(mediaData?.id,
                                            mediaData?.title?.rendered,
                                            mediaDetails?.thumbnail?.sourceUrl,
                                            mediaDetails?.medium?.sourceUrl,
                                            mediaDetails?.full?.sourceUrl)
                                }
                            }

                            val categories:String = post.categories.toString()
                            val tags:String = post.tags.toString()
                            val commentStatus:String = post.commentStatus

                            val table = PostTable(id, date, author,
                                    title,content,categories,tags,commentStatus,mediaTable)

                            //insert data
                            postTableDao?.insert(table)
                            Timber.i("Table${table.post_id} insert: ${table.post_title}")
                        }

                        Timber.i("Data insert complete")

                        //all data saved
                        //now call fill data
                        fillData()
                        //now request for more data is open
                        MainActivity.request = false
                    })
                }

            }

        })
    }


    fun saveDate(context: Context) {

        val pattern = "yyyy-MM-dd'T'HH:mm:ss"
        val dtf = SimpleDateFormat(pattern, Locale.getDefault())

        //current date and time
        var today: Date = dtf.parse(dtf.format(Date()))

        for (n in dateList) {
            if (dateCheckedList.contains(n)) return
            val date2 = dtf.parse(n)
            val date3 = MyDateUtil.compareTwoDate(today, date2)

            Timber.i("Date:$date2 and $today -> $date3")

            today = date3
            dateCheckedList.add(n)
        }
        val spSave = dtf.format(today)
        MyDateUtil.setDateOnSp(context, spSave)
        Timber.i("date saved: $spSave")
    }

    //get data from database and add data to the mutable live data list
    private fun fillData() {

        val postData = postTableDao?.getPostData

        //todo 1/3/2018 Use paging library to prevent load all data at once

        if (postData != null && postData.isNotEmpty()) {

            Timber.i("Data found on database. Size: ${postData.size}")

            for (post in postData) {
                val date = post.date ?: ConstantUtil.dateSpDefaultValue
                if (!dateList.contains(date)) {
                    dateList.add(date)
                }
            }

            //put the data
            allPost!!.postValue(postData)

        } else {
            //id data is empty
            Timber.i("Database has no data")
            addRemoteData(null)
        }
    }

    fun requestNewPost(wpRestInterface: WPRestInterface,
                       date: String) {

        AsyncTask.execute({
            val call = wpRestInterface.getFilterPostList(date)
            Timber.i("Request for new query data")
            Timber.i("Date before: $date")
            addRemoteData(call)
            //show toast message
            MainActivity.showNewDataToast = true
            Timber.i("new request finished")
        })

    }
}