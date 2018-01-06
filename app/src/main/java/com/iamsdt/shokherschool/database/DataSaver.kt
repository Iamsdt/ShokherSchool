package com.iamsdt.shokherschool.database

import com.iamsdt.shokherschool.retrofit.RetrofitData
import com.iamsdt.shokherschool.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by Shudipto Trafder on 11/27/2017.
 * at 11:21 PM
 */
class DataSaver(private val database: MyDatabase) {

    private var wordPressResponse: WPRestInterface? = null

    private var retHandler: RetrofitHandler? = null

    private val authorList = ArrayList<Int>()
    private val mediaList = ArrayList<Int>()

    init {
        val retrofitData = RetrofitData()
        wordPressResponse = retrofitData.wpRestInterface
        retHandler = RetrofitHandler(wordPressResponse!!)
    }

    fun syncData() {
        wordPressResponse!!.getAllPostList()
                .enqueue(object : Callback<List<PostResponse>> {
                    override fun onFailure(call: Call<List<PostResponse>>?,
                                           t: Throwable?) {
                        //Utility.logger("default post not found","Main request",t)
                    }

                    override fun onResponse(call: Call<List<PostResponse>>?,
                                            response: Response<List<PostResponse>>?) {
                        saveDataToDatabase(response?.body())
                    }
                })
    }

    fun requestNewData(date: String) {
        val postData = retHandler?.getFilterPostList(date)?.value
        saveDataToDatabase(postData)
    }

    private fun saveDataToDatabase(data: List<PostResponse>?) {

        //if no data
        //or empty
        //then return
        if (data == null || data.isEmpty()) {
            return
        }

        //debug only 12/8/2017 remove later
        val insertList = ArrayList<Long>()

        for (post in data) {
            val id = post.id
            val title = post.title?.rendered
            val date = post.date

            val author = post.author
            val media = post.featuredMedia

            //check first then insert author and media
            if (!authorList.contains(author)) {
                authorList.add(author)
            }

            if (!mediaList.contains(media)) {
                mediaList.add(media)
            }

//            val table = PostTable(id, date, author, title, media)
//
//            //insert data
//            val insert = database.postTableDao.insert(table)
//            insertList.add(insert)
        }

        //Utility.logger("Post inserted: ${insertList.size}", "Post Inserted")


        saveAuthorAndMedia()
    }

    private fun saveAuthorAndMedia() {

        for (media in mediaList) {
            val liveData = retHandler?.getMediaByID(media)?.value
            val id = liveData!!.id
            val mediaDetails = liveData.mediaDetails?.sizes
            //fixme 12/8/2017 this create a  problem that is it will never update later
//            if (database.mediaTableDao.getMediaID(id) != media) {
//                val mediaTable = MediaTable(id, liveData.title?.rendered,
//                        mediaDetails?.thumbnail?.sourceUrl,
//                        mediaDetails?.medium?.sourceUrl,
//                        mediaDetails?.full?.sourceUrl)
//
//                database.mediaTableDao.insert(mediaTable)
//            }

        }

        for (author in authorList) {
            val liveData = retHandler?.getAuthorByID(author)?.value
            val id = liveData!!.id
            //fixme 12/8/2017 this create a  problem that is it will never update later
//            if (database.authorTableDao.getAuthorID(id) != author) {
//                val authorTable = AuthorTable(liveData.avatarUrls?.avatar24,
//                        liveData.avatarUrls?.avatar48, liveData.avatarUrls?.avatar96,
//                        liveData.name, liveData.link, liveData.description, id)
//
//                database.authorTableDao.insert(authorTable)
//            }
        }
    }
}