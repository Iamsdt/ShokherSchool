package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.model.PostModel

/**
 * Created by Shudipto Trafder on 1/12/2018.
 * at 4:49 PM
 */

class BookmarkViewModel(application: Application):
        AndroidViewModel(application){

    private var postData:MutableLiveData<List<PostModel>> ?= null

    fun getData(postTableDao: PostTableDao):MutableLiveData<List<PostModel>>? {
        if (postData == null){
            postData = MutableLiveData()
            fillData(postTableDao)
        }

        return postData
    }

    private fun fillData(postTableDao: PostTableDao){
        AsyncTask.execute({
            //1 for true
            val data = postTableDao.getBookmarkData(1)
            postData?.postValue(data)
        })
    }
}