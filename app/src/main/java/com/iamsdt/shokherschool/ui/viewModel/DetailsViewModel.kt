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
            htmlData!!.postValue(model)
        })
    }
}