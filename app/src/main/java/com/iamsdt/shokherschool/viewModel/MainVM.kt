package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import com.iamsdt.shokherschool.activity.MainActivity
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.database.table.AuthorTable
import com.iamsdt.shokherschool.database.table.MediaTable
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

    private var dateList = ArrayList<String>()
    private var dateCheckedList = ArrayList<String>()


    fun getAllPostList(postTableDao: PostTableDao,
                       mediaTableDao: MediaTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface)
            : MutableLiveData<List<PostModel>>? {

        if (allPost == null) {

            allPost = MutableLiveData()

            val service = Executors.newSingleThreadExecutor()
            service.submit({
                val data = postTableDao.getFirst10DataList

                /**but in real, data must be added to database
                 * data request, from splash activity
                 * if data found then come to main activity
                 * until any exception happen this list will filled
                 * **/
                if (data.isEmpty()) {

                    //add remote data to database
                    addRemoteData(postTableDao,
                            mediaTableDao,
                            authorTableDao,wpRestInterface,
                            null)
                } else {
                    //data is present
                    // saved data to all post
                    fillData(postTableDao)
                }
            })
        }

        return allPost
    }

    private fun addRemoteData(postTableDao: PostTableDao,
                              mediaTableDao: MediaTableDao,
                              authorTableDao: AuthorTableDao,
                              wpRestInterface: WPRestInterface,
                              callback: Call<List<PostResponse>>?) {

        var call = callback

        //if call is null make default request
        if (call == null){
            //make request to database
            call = wpRestInterface.getAllPostList()
        }

        call.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Timber.e(t, "post data failed")
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                val authorInserted = ArrayList<Int>()
                val mediaInserted = ArrayList<Int>()

                if (response!!.isSuccessful) {

                    AsyncTask.execute({

                        val postData = response.body()

                        for (post in postData!!) {
                            val id = post.id
                            val title = post.title?.rendered
                            val date = post.date

                            //add date to date list
                            if (dateList.contains(date)) {
                                dateList.add(date)
                            }

                            //author id
                            val author = post.author
                            if (!authorInserted.contains(author)) {
                                //request data from server
                                val authorResponse = wpRestInterface.getAuthorByID(author).execute()

                                if (authorResponse.isSuccessful) {
                                    val authorData = authorResponse.body()
                                    val authorTable = AuthorTable(
                                            authorData?.avatarUrls?.avatar24,
                                            authorData?.avatarUrls?.avatar48,
                                            authorData?.avatarUrls?.avatar96,
                                            authorData?.name, authorData?.link,
                                            authorData?.description, authorData?.id)

                                    authorTableDao.insert(authorTable)

                                    authorInserted.add(author)
                                }
                            }

                            //media id
                            val media = post.featuredMedia
                            if (!mediaInserted.contains(media)) {
                                //request to server
                                val mediaResponse = wpRestInterface.getMediaByID(media).execute()

                                if (mediaResponse.isSuccessful) {
                                    //data from server
                                    val mediaData = mediaResponse.body()
                                    //media image size
                                    val mediaDetails = mediaData?.mediaDetails?.sizes

                                    val mediaTable = MediaTable(mediaData?.id,
                                            mediaData?.title?.rendered,
                                            mediaDetails?.thumbnail?.sourceUrl,
                                            mediaDetails?.medium?.sourceUrl,
                                            mediaDetails?.full?.sourceUrl)

                                    mediaTableDao.insert(mediaTable)

                                    //now save this id to array list
                                    mediaInserted.add(media)
                                }

                            }

                            val table = PostTable(id, date, author,
                                    title, media)

                            //insert data
                            postTableDao.insert(table)
                        }

                        //all data saved
                        //now call fill data
                        fillData(postTableDao)
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
            today = MyDateUtil.compareTwoDate(today, date2)
            dateCheckedList.add(n)
        }
        val spSave = dtf.format(today)
        MyDateUtil.setDateOnSp(context, spSave)
        Timber.i("date saved start: $spSave")
    }

    //get data from database and add data to the mutable live data list
    private fun fillData(postTableDao: PostTableDao) {

        AsyncTask.execute({

            val arrayList = ArrayList<PostModel>()

            val postData = postTableDao.getPostData()

            for (post in postData) {
                arrayList.add(post)
            }

            //put the data
            allPost!!.postValue(arrayList)

        })
    }

    fun requestNewPost(postTableDao: PostTableDao,
                       mediaTableDao: MediaTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface,
                       date: String) {

        AsyncTask.execute({
            val call = wpRestInterface.getFilterPostList(date)
            addRemoteData(postTableDao, mediaTableDao, authorTableDao,wpRestInterface, call)
            fillData(postTableDao)
            MainActivity.request = false
        })

    }
}