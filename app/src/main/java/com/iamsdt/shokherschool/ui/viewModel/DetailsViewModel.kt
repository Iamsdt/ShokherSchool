package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.model.DetailsPostModel

/**
 * Created by Shudipto Trafder on 11/21/2017.
 * at 11:20 PM
 */
class DetailsViewModel(application: Application) :
        AndroidViewModel(application) {

    private var htmlData: MutableLiveData<DetailsPostModel>? = null

    fun getData(postID: Int,postTableDao: PostTableDao): MutableLiveData<DetailsPostModel>? {

        if (htmlData == null) {
            htmlData = MutableLiveData()

            initializeData(postID,postTableDao)
        }

        return htmlData
    }


    private fun initializeData(id: Int,postTableDao: PostTableDao) {

        AsyncTask.execute({
            val model = postTableDao.getSinglePostDetails(id)
            val tagsList = model.tags?.split(",") ?: arrayListOf()
            val categoriesList = model.categories?.split(",") ?: arrayListOf()

            var tags = ""
            for (t in tagsList){
                val id = t.trim()

            }

            var categories = ""
            for (c in categoriesList){
                val id = c.trim()
            }

            //create new model and add tags and categories
            val detailsPostModel = DetailsPostModel(model.id,
                    model.date,
                    model.title,
                    model.content,
                    model.authorName,
                    model.authorDetails,
                    model.authorImg,
                    model.mediaLink,tags,categories)
            htmlData!!.postValue(detailsPostModel)
        })
    }
}