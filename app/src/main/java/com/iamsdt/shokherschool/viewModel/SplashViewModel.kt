package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.database.table.AuthorTable
import com.iamsdt.shokherschool.database.table.MediaTable
import com.iamsdt.shokherschool.database.table.PostTable
import com.iamsdt.shokherschool.model.PostModel
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 1/1/2018.
 * at 8:50 PM
 */

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private var allPost: MutableLiveData<List<PostModel>>? = null

    //mock data list
    private val list = ArrayList<PostModel>()

    fun getAllPostList(postTableDao: PostTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface)
            : MutableLiveData<List<PostModel>>? {

        if (allPost == null) {

            allPost = MutableLiveData()

            AsyncTask.execute({
                val data = postTableDao.getFirst10DataList
                if (data.isEmpty()) {

                    Timber.i("No data in database")
                    addRemoteData(postTableDao,
                            authorTableDao,
                            wpRestInterface)

                } else {
                    Timber.i("Database has data")
                    addMockData()
                }
            })
        }

        return allPost
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
                            var mediaTable:MediaTable ?= null

                            if (media != 0){

                                val mediaResponse = wpRestInterface.getMediaByID(media).execute()

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

                            val table = PostTable(id, date, author,
                                    title,content, media,mediaTable)

                            //insert data
                            postTableDao.insert(table)
                        }

                        Timber.i("data save to database")

                        //now add mock data
                        addMockData()
                    })
                }

            }

        })
    }

    private fun addMockData() {
        // don't need to fill with real data data to all post
        // just add some mock data
        // this data is not needed
        list.add(PostModel())
        allPost!!.postValue(list)

        Timber.i("Mock data added")
    }
}
