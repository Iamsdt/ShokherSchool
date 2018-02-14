package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.table.AuthorTable
import com.iamsdt.shokherschool.data.database.table.MediaTable
import com.iamsdt.shokherschool.data.database.table.PostTable
import com.iamsdt.shokherschool.data.model.EventMessage
import com.iamsdt.shokherschool.data.model.PostModel
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 10:59 PM
 */
class MainVM(application: Application) : AndroidViewModel(application) {

    private var allPost:LiveData<List<PostModel>> ?= null

    private val authorInserted = ArrayList<Int>()

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

    fun getAllPostList(): LiveData<List<PostModel>>? {

        /** live data is not counted until it has an active observer **/
        allPost = postTableDao?.getPostData

        return allPost
    }

    private fun addRemoteData(call: Call<List<PostResponse>>, bus: EventBus) {

        Timber.i("call start for Remote data")

        call.enqueue(object : Callback<List<PostResponse>> {
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
                            var mediaTable: MediaTable? = null

                            if (media != 0) {

                                val mediaResponse = wpRestInterface?.getMediaByID(media)?.execute()

                                if (mediaResponse!!.isSuccessful) {
                                    val mediaData = mediaResponse.body()
                                    //media image size
                                    val mediaDetails = mediaData?.mediaDetails?.sizes

                                    mediaTable = MediaTable(mediaData?.id,
                                            mediaData?.title?.rendered,
                                            mediaDetails?.medium?.sourceUrl)
                                }
                            }

                            var categories: String = post.categories.toString()
                            var tags: String = post.tags.toString()

                            //categories list will be [1,2,3,4,5]
                            // so remove '[' and ']'
                            val array = charArrayOf('[', ']')
                            //to convert array to vararg use *
                            categories = categories.trim(*array)

                            //same for tags
                            tags = tags.trim(*array)


                            val commentStatus: String = post.commentStatus

                            val table = PostTable(id, date, author,
                                    title, content, categories, tags, commentStatus, 0, mediaTable)

                            //insert data
                            postTableDao?.insert(table)
                            Timber.i("Table${table.post_id} insert: ${table.post_title}")
                        }

                        Timber.i("Data insert complete")

                        //event bus
                        bus.post(EventMessage(key = ConstantUtil.NEW_POST_FOUND,message = "found new post"))
                    })
                }

            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        allPost = null
    }

    fun requestNewPost(wpRestInterface: WPRestInterface,
                       date: String,bus: EventBus) {
        val call = wpRestInterface.getFilterPostList(date)
        Timber.i("Request for new query data")
        Timber.i("Date before: $date")
        addRemoteData(call,bus)
        //show toast message
        //MainActivity.showNewDataToast = true
        Timber.i("new request finished")
    }
}