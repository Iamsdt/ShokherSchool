package com.iamsdt.shokherschool.ui.services

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.table.AuthorTable
import com.iamsdt.shokherschool.data.database.table.MediaTable
import com.iamsdt.shokherschool.data.database.table.PostTable
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.ui.base.BaseServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("Registered")
/**
 * Created by Shudipto Trafder on 2/10/2018.
 * at 11:09 PM
 */
class PostDataService : BaseServices() {

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var authorTableDao: AuthorTableDao
    @Inject lateinit var wpRestInterface: WPRestInterface

    var isRunning = false
    private var postInsertComplete = false

    fun isServiceCompleted(): Boolean {
        return postInsertComplete
    }

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()

        isRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        addRemoteData(postTableDao,authorTableDao,wpRestInterface)

        startService(Intent(this,DataInsertService::class.java))

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun addRemoteData(postTableDao: PostTableDao,
                              authorTableDao: AuthorTableDao,
                              wpRestInterface: WPRestInterface) {
        //make request to server
        val call = wpRestInterface.getAllPostList()
        call.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Timber.e(t, "post data failed")
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                val authorInserted = ArrayList<Int>()

                if (response!!.isSuccessful) {

                    Timber.i("Response found from server")

                    AsyncTask.execute({

                        val postData = response.body()

                        for (post in postData!!) {
                            val id = post.id
                            val title = post.title?.rendered
                            val content = post.content?.rendered
                            val date = post.date

                            //author id
                            val author = post.author
                            if (!authorInserted.contains(author)) {
                                //request data from server
                                val authorResponse =
                                        wpRestInterface.getAuthorByID(author).execute()

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

                            val media = post.featuredMedia
                            var mediaTable: MediaTable?= null

                            if (media != 0){

                                val mediaResponse = wpRestInterface.getMediaByID(media).execute()

                                if (mediaResponse!!.isSuccessful) {
                                    val mediaData = mediaResponse.body()
                                    //media image size
                                    val mediaDetails = mediaData?.mediaDetails?.sizes

                                    mediaTable = MediaTable(mediaData?.id,
                                            mediaData?.title?.rendered,
                                            mediaDetails?.medium?.sourceUrl)
                                }
                            }

                            var categories:String = post.categories.toString()
                            var tags:String = post.tags.toString()
                            val commentStatus:String = post.commentStatus

                            //categories list will be [1,2,3,4,5]
                            // so remove '[' and ']'
                            val array = charArrayOf('[',']')
                            //to convert array to vararg use *
                            categories = categories.trim(*array)

                            //same for tags
                            tags = tags.trim(*array)

                            //0 for bookmark
                            val table = PostTable(id, date, author,
                                    title,content,categories,tags,commentStatus,0,mediaTable)

                            //insert data
                            postTableDao.insert(table)
                        }

                        Timber.i("post data save to database")
                    })
                }

            }

        })
    }

}