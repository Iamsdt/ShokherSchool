package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
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
import com.iamsdt.shokherschool.utilities.MyDateUtil
import com.iamsdt.shokherschool.utilities.MyDateUtil.Companion.getReadableDate
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

    private var dateList = ArrayList<String>()
    private var dateCheckedList = ArrayList<String>()


    fun getAllPostList(postTableDao:PostTableDao,
                       mediaTableDao: MediaTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface)
            : MutableLiveData<List<PostModel>>?{

        if (allPost == null){
            val service = Executors.newSingleThreadExecutor()
            service.submit({
                val data = postTableDao.getAllDataList
                if (data.isEmpty()) {
                    addRemoteData(postTableDao,
                            mediaTableDao,
                            authorTableDao,
                            wpRestInterface)
                }else{
                    fillData(postTableDao,mediaTableDao,authorTableDao)
                }
            })
        }

        return allPost
    }

    private fun addRemoteData(postTableDao:PostTableDao,
                              mediaTableDao: MediaTableDao,
                              authorTableDao: AuthorTableDao,
                              wpRestInterface: WPRestInterface) {
        val postResponse = wpRestInterface.getAllPostList()

        remoteToDatabase(postTableDao,mediaTableDao,authorTableDao,wpRestInterface,postResponse)
    }

    private fun remoteToDatabase(postTableDao:PostTableDao,
                                 mediaTableDao: MediaTableDao,
                                 authorTableDao: AuthorTableDao,
                                 wpRestInterface: WPRestInterface,
                                 call: Call<List<PostResponse>>) {

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
                            postTableDao.insert(table)
                        }

                        saveMediaAndAuthor(mediaTableDao,authorTableDao,wpRestInterface)

                        //now data saved
                        fillData(postTableDao,mediaTableDao,authorTableDao)
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

    //get data from database and add data to the mutable live data list
    private fun fillData(postTableDao: PostTableDao,
                         mediaTableDao: MediaTableDao,
                         authorTableDao: AuthorTableDao) {

        if (allPost == null){
            allPost = MutableLiveData()
        }

        AsyncTask.execute({

            val arrayList = ArrayList<PostModel>()

            val postData = postTableDao.getAllDataList

            if (!postData.isEmpty()){

                for (post in postData) {

                    val authorName = authorTableDao.
                            getAuthorName(post.author!!)

                    val mediaLink = mediaTableDao.
                            getMediaThumbnail(post.featuredMedia!!)

                    val newModel = PostModel(
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

    private fun saveMediaAndAuthor(mediaTableDao: MediaTableDao,
                                   authorTableDao: AuthorTableDao,
                                   wpRestInterface: WPRestInterface) {

        if (mediaIdArray.isEmpty() && authorIdArray.isEmpty()){
            return

        } else if (!mediaIdArray.isEmpty()){

            val ids = mediaTableDao.getMediaIds()

            for (media in mediaIdArray) {
                //fixme 12/8/2017 this create a  problem that is it will never update later

                if (ids.contains(media))continue

                val response = wpRestInterface.getMediaByID(media).execute()

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

                mediaTableDao.insert(mediaTable)
            }

        } else if (!authorIdArray.isEmpty()){

            val ids = authorTableDao.getAuthorIds()

            for (author in authorIdArray) {
                //fixme 12/8/2017 this create a  problem that is it will never update later
                if (ids.contains(author)) {
                    continue
                }

                val response = wpRestInterface.getAuthorByID(author).execute()

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

                authorTableDao.insert(authorTable)
            }
        }
    }

    fun requestNewPost(postTableDao:PostTableDao,
                       mediaTableDao: MediaTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface,
                       date: String) {
        val call = wpRestInterface.getFilterPostList(date)
        remoteToDatabase(postTableDao,mediaTableDao,authorTableDao,wpRestInterface,call)
    }
}