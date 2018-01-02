package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.model.PostModel
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.utilities.DataInsert.Companion.addRemoteData
import com.iamsdt.shokherschool.utilities.MyDateUtil
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


    private var dateList = ArrayList<String>()
    private var dateCheckedList = ArrayList<String>()


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

                /**but in real, data must be added to database
                 * data request, from splash activity
                 * if data found then come to main activity
                 * until any exception happen this list will filled
                 * **/
                if (data.isEmpty()) {
                    //add remote data to database
                    addRemoteData(postTableDao,
                            mediaTableDao,
                            authorTableDao,
                            wpRestInterface,null)

                    // data is saved to database
                    //now fill the data to all post
                    fillData(postTableDao)
                } else {
                    //data is present
                    // saved data to all post
                    fillData(postTableDao)
                }
            })
        }

        return allPost
    }


    fun saveDate(context: Context) {

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
    private fun fillData(postTableDao: PostTableDao) {

        AsyncTask.execute({

            val arrayList = ArrayList<PostModel>()

            val postData = postTableDao.getPostData()

            for (post in postData) {
                arrayList.add(post)
            }

            //put the data
            allPost!!.postValue(arrayList)

        })
    }

    fun requestNewPost(postTableDao: PostTableDao,
                       mediaTableDao: MediaTableDao,
                       authorTableDao: AuthorTableDao,
                       wpRestInterface: WPRestInterface,
                       date: String) {
        val call = wpRestInterface.getFilterPostList(date)
        addRemoteData(postTableDao, mediaTableDao, authorTableDao,wpRestInterface, call)
    }
}