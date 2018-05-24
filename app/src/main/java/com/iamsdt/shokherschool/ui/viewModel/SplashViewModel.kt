package com.iamsdt.shokherschool.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.model.PostModel
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
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

                } else {
                    Timber.i("Database has data")
                    addMockData()
                }
            })
        }

        return allPost
    }



    /**
     * Add mock data to the Mutable Live data
     */
    private fun addMockData() {
        // don't need to fill with real data data to all post
        // just add some mock data
        // this data is not needed
        list.add(PostModel())
        allPost!!.postValue(list)

        Timber.i("Mock data added")
    }
}
