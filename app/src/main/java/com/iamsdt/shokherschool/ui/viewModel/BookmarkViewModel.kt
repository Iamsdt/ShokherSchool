package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.model.PostModel

/**
 * Created by Shudipto Trafder on 1/12/2018.
 * at 4:49 PM
 */

class BookmarkViewModel(application: Application):
        AndroidViewModel(application){

    private var postData: LiveData<List<PostModel>>?= null

    fun getData(postTableDao: PostTableDao):LiveData<List<PostModel>>? {
        postData = postTableDao.getBookmarkData()
        return postData
    }

}