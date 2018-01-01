package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
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
import java.util.concurrent.Executors

/**
 * Created by Shudipto Trafder on 1/1/2018.
 * at 8:50 PM
 */

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private var allPost: MutableLiveData<List<PostModel>>? = null

    //author id and media id
    // add this ids in different table
    private var authorIdArray = ArrayList<Int>()
    private var mediaIdArray = ArrayList<Int>()

    //mock data list
    private val list = ArrayList<PostModel>()

    fun getAllPostList(postTableDao: PostTableDao,
                       mediaTableDao: MediaTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface)
            : MutableLiveData<List<PostModel>>? {

        if (allPost == null) {

            allPost = MutableLiveData()

            val service = Executors.newSingleThreadExecutor()
            service.submit({
                val data = postTableDao.getAllDataList
                if (data.isEmpty()) {
                    addRemoteData(postTableDao,
                            mediaTableDao,
                            authorTableDao,
                            wpRestInterface)
                } else {
                    // don't fill data to all post
                    // just add some mock data
                    // this data is not needed
                    list.add(PostModel())
                    allPost!!.postValue(list)
                }
            })
        }

        return allPost
    }


    //This method add data remote to database
    private fun addRemoteData(postTableDao: PostTableDao,
                              mediaTableDao: MediaTableDao,
                              authorTableDao: AuthorTableDao,
                              wpRestInterface: WPRestInterface) {
        //response form server
        val postResponse = wpRestInterface.getAllPostList()

        postResponse.enqueue(object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                Timber.e(t, "post data failed")
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                if (response!!.isSuccessful) {

                    AsyncTask.execute({

                        val postData = response.body()

                        for (post in postData!!) {
                            val id = post.id
                            val title = post.title?.rendered
                            val date = post.date

                            val author = post.author

                            if (!authorIdArray.contains(author)) {
                                authorIdArray.add(author)
                            }

                            val media = post.featuredMedia

                            if (!mediaIdArray.contains(media)) {
                                mediaIdArray.add(media)
                            }

                            val table = PostTable(id, date, author,"",
                                    title, media)

                            //insert data
                            postTableDao.insert(table)
                        }

                        saveMediaAndAuthor(mediaTableDao, authorTableDao, wpRestInterface)
                    })
                }

            }

        })
    }


    //This method for save author data and media data to database
    private fun saveMediaAndAuthor(mediaTableDao: MediaTableDao,
                                   authorTableDao: AuthorTableDao,
                                   wpRestInterface: WPRestInterface) {

        //if no data no need to execute
        if (mediaIdArray.isEmpty() && authorIdArray.isEmpty()) {
            return
        }

        AsyncTask.execute({

            if (!mediaIdArray.isEmpty()) {

                for (media in mediaIdArray) {

                    val response = wpRestInterface.getMediaByID(media).execute()

                    if (!response!!.isSuccessful) {
                        continue
                    }

                    val mediaResponse = response.body()
                    val mediaDetails = mediaResponse?.mediaDetails?.sizes
                    val mediaTable = MediaTable(mediaResponse?.id,
                            mediaResponse?.title?.rendered,
                            mediaDetails?.thumbnail?.sourceUrl,
                            mediaDetails?.medium?.sourceUrl,
                            mediaDetails?.full?.sourceUrl)

                    mediaTableDao.insert(mediaTable)
                }

            }
            else if (!authorIdArray.isEmpty()) {

                for (author in authorIdArray) {

                    val response = wpRestInterface.getAuthorByID(author).execute()

                    if (!response!!.isSuccessful) {
                        continue
                    }

                    val authorResponse = response.body()
                    val authorTable = AuthorTable(
                            authorResponse?.avatarUrls?.avatar24,
                            authorResponse?.avatarUrls?.avatar48,
                            authorResponse?.avatarUrls?.avatar96,
                            authorResponse?.name, authorResponse?.link,
                            authorResponse?.description, authorResponse?.id)

                    authorTableDao.insert(authorTable)
                }
            }

            //after finish add some mock data
            list.add(PostModel())
            allPost!!.postValue(list)
        })
    }
}
