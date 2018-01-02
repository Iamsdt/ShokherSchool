package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.model.PostModel
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.utilities.DataInsert.Companion.addRemoteData
import java.util.concurrent.Executors

/**
 * Created by Shudipto Trafder on 1/1/2018.
 * at 8:50 PM
 */

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private var allPost: MutableLiveData<List<PostModel>>? = null

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
                val data = postTableDao.getFirst10DataList
                if (data.isEmpty()) {
                    addRemoteData(postTableDao,
                            mediaTableDao,
                            authorTableDao,
                            wpRestInterface,null,true)

                    if (requestComplete){
                        addMockData()
                    }

                } else {
                    addMockData()
                }
            })
        }

        return allPost
    }

    private fun addMockData(){
        // don't need to fill with real data data to all post
        // just add some mock data
        // this data is not needed
        list.add(PostModel())
        allPost!!.postValue(list)
    }

    companion object {
        var requestComplete = false
    }
}
