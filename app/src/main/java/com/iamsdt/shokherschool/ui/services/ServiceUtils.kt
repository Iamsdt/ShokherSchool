package com.iamsdt.shokherschool.ui.services

import android.os.AsyncTask
import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.data.database.table.*
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.retrofit.pojo.categories.CategoriesResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.page.PageResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.tags.TagResponse
import com.iamsdt.shokherschool.data.utilities.ConstantUtil.Companion.ERROR
import com.iamsdt.shokherschool.data.utilities.ConstantUtil.Companion.SUCCESS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 2/11/2018.
 * at 10:28 AM
 */
class ServiceUtils {
    companion object {

        /**
         * Method for add post data from server
         * and save to database
         *
         * @param postTableDao post table access
         * @param authorTableDao access author table
         * @param wpRestInterface retrofit interface
         *
         * @return sate of this method in String
         */
        fun addPostData(postTableDao: PostTableDao,
                        authorTableDao: AuthorTableDao,
                        wpRestInterface: WPRestInterface): HashMap<String, String> {
            //make request to server
            val hashMap = HashMap<String, String>()

            try {
                val call = wpRestInterface.getAllPostList()
                call.enqueue(object : Callback<List<PostResponse>> {
                    override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                        Timber.e(t, "post data failed")
                        hashMap[ERROR] = "error on response : ${t?.message}"
                    }

                    override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {

                        val authorInserted = ArrayList<Int>()

                        if (response!!.isSuccessful) {

                            Timber.i("Response found from server for post data")

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
                                    var mediaTable: MediaTable? = null

                                    if (media != 0) {

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

                                    var categories: String = post.categories.toString()
                                    var tags: String = post.tags.toString()
                                    val commentStatus: String = post.commentStatus

                                    //categories list will be [1,2,3,4,5]
                                    // so remove '[' and ']'
                                    val array = charArrayOf('[', ']')
                                    //to convert array to vararg use *
                                    categories = categories.trim(*array)

                                    //same for tags
                                    tags = tags.trim(*array)

                                    //0 for bookmark
                                    val table = PostTable(id, date, author,
                                            title, content, categories, tags, commentStatus, 0, mediaTable)

                                    //insert data
                                    postTableDao.insert(table)
                                }

                                Timber.i("post data save to database")
                                hashMap[SUCCESS] = "post data insert complete"
                            })
                        }
                    }
                })

            } catch (e: Exception) {
                Timber.e("Error on data insert ${e.message}")
                hashMap[ERROR] = "Error on data insert ${e.message}"
            }

            return hashMap
        }

        /**
         * Method for add tag data to database
         *
         * @param tagTableDao access tag table
         * @param wpRestInterface retrofit interface
         * */
        fun addTagData(tagTableDao: TagTableDao, wpRestInterface: WPRestInterface):
                HashMap<String, String> {

            val hashMap = HashMap<String, String>()

            try {
                val tagCall = wpRestInterface.getTags()

                tagCall.enqueue(object : Callback<List<TagResponse>> {
                    override fun onResponse(call: Call<List<TagResponse>>?,
                                            response: Response<List<TagResponse>>?) {

                        //if something wrong a empty list will crated
                        val tags = response?.body() ?: arrayListOf()

                        AsyncTask.execute({

                            tags.map { TagTable(it.count, it.name, it.id) }
                                    .forEach { tagTableDao.insert(it) }
                            Timber.i("tag insert finished")
                            hashMap[SUCCESS] = "tag insert finished"
                        })

                    }

                    override fun onFailure(call: Call<List<TagResponse>>?, t: Throwable?) {
                        Timber.i(t, "tag Response error ${t?.message}")
                        hashMap[ERROR] = "tag Response error ${t?.message}"
                    }

                })
            } catch (e: Exception) {
                Timber.e(e, "Exception on tag data insert ${e.message}")
                hashMap[SUCCESS] = "Exception on tag data insert: ${e.message}"
            }

            return hashMap
        }

        /***
         * method for insert categories data
         *
         * @param categoriesTableDao access categories table
         * @param wpRestInterface retrofit interface
         *
         * @return hashMap with success or error message
         */
        fun addCategoriesData(categoriesTableDao: CategoriesTableDao,
                              wpRestInterface: WPRestInterface): HashMap<String, String> {

            val hashMap = HashMap<String, String>()

            try {
                val categoriesCall = wpRestInterface.getCategories()

                categoriesCall.enqueue(object : Callback<List<CategoriesResponse>> {
                    override fun onResponse(call: Call<List<CategoriesResponse>>?,
                                            response: Response<List<CategoriesResponse>>?) {

                        //if something wrong a empty list will crated
                        val categories = response?.body() ?: arrayListOf()

                        AsyncTask.execute({

                            for (category in categories) {
                                val table = CategoriesTable(category.count, category.name,
                                        category.description, category.id)

                                categoriesTableDao.insert(table)
                            }

                            Timber.i("category insert finished")
                            hashMap[SUCCESS] = "category insert finished"
                        })

                    }

                    override fun onFailure(call: Call<List<CategoriesResponse>>?, t: Throwable?) {
                        Timber.i(t, "Categories Response error ${t?.message}")
                        hashMap[ERROR] = "Categories Response error ${t?.message}"
                    }

                })
            } catch (e: Exception) {
                Timber.e(e, "Error on categories data insert ${e.message}")
                hashMap[ERROR] = "Categories Response error ${e.message}"
            }

            return hashMap
        }

        /***
         * method for insert categories data
         *
         * @param pageTableDao access page table
         * @param wpRestInterface retrofit interface
         */
        fun addPageData(pageTableDao: PageTableDao, wpRestInterface: WPRestInterface):
                HashMap<String, String> {

            val hashMap = HashMap<String,String>()

            try {
                val pageCall = wpRestInterface.getPages()

                pageCall.enqueue(object : Callback<List<PageResponse>> {
                    override fun onResponse(call: Call<List<PageResponse>>?,
                                            response: Response<List<PageResponse>>?) {

                        val pages = response?.body() ?: arrayListOf()

                        AsyncTask.execute({

                            for (page in pages) {
                                val table = PageTable(page.date, page.author, page.title?.rendered,
                                        page.content?.rendered,
                                        page.featuredMedia, page.id)

                                pageTableDao.insert(table)
                            }

                            Timber.i("page insert finished")
                            hashMap[SUCCESS] = "page insert finished"
                        })
                    }

                    override fun onFailure(call: Call<List<PageResponse>>?, t: Throwable?) {
                        Timber.i(t, "Categories Response error ${t?.message}")
                        hashMap[ERROR] = "Categories Response error ${t?.message}"
                    }

                })
            } catch (e: Exception) {
                Timber.e(e, "Error on page data insert ${e.message}")
                hashMap[ERROR] = "Categories Response error ${e.message}"
            }

            return hashMap
        }
    }
}