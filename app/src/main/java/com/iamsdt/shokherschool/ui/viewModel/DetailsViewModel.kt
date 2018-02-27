package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.os.HandlerThread
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.dao.TagTableDao
import com.iamsdt.shokherschool.data.model.DetailsPostModel

/**
 * Created by Shudipto Trafder on 11/21/2017.
 * at 11:20 PM
 */
class DetailsViewModel(application: Application) :
        AndroidViewModel(application) {

    private var htmlData : LiveData<DetailsPostModel>? = null

    //not needed any more
    private var fullData:MutableLiveData<DetailsPostModel> ?= null

    /**
     * Get data from database
     * @param postID id of desired post
     * @param postTableDao Data Accessing object for post table
     *
     * @return live data
     */
    fun getData(postID: Int, postTableDao: PostTableDao): LiveData<DetailsPostModel>? {
        htmlData = postTableDao.getSinglePostDetails(postID)
        return htmlData
    }


    //need to fill data
    //don't use any more

    /**
     * Get data from database
     * @param details DetailsPostModel class
     * @param categoriesTableDao Data Accessing object for Categories table
     * @param tagTableDao Data Accessing object for Tag table
     * @return live data
     */
    private fun fillData(details:DetailsPostModel?,
                               categoriesTableDao: CategoriesTableDao,
                               tagTableDao: TagTableDao):
    MutableLiveData<DetailsPostModel>? {

        if (details == null){
            return null
        }

        fullData = MutableLiveData()

        val thread = HandlerThread("DetailsData")
        thread.start()

        val handler = Handler(thread.looper)

        handler.post({
            val tagsList = details.tags?.split(",") ?: arrayListOf()
            val categoriesList = details.categories?.split(",") ?: arrayListOf()

            var tags = ""
            tagsList.filterNot { it.isEmpty() }
                    .map { it.trim().toInt() }
                    .forEach { tags += tagTableDao.getTagName(it) + ", " }

            var categories = ""
            categoriesList.filterNot { it.isEmpty() }
                    .map { it.trim().toInt() }
                    .forEach { categories += categoriesTableDao.getCategoriesName(it) + ", " }

            //create new model and add tags and categories
            val detailsPostModel = DetailsPostModel(details.id,
                    details.date,
                    details.title,
                    details.content,
                    details.authorName,
                    details.authorDetails,
                    details.authorImg,
                    details.mediaLink, tags, categories)

            fullData?.postValue(detailsPostModel)
        })

        return fullData
    }
}