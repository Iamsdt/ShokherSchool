package com.iamsdt.shokherschool.ui.services

import android.annotation.SuppressLint
import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.ui.base.BaseServices
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPostData
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("Registered")
/**
 * Created by Shudipto Trafder on 2/10/2018.
 * at 11:09 PM
 */
class PostDataService : BaseServices() {

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var wpRestInterface: WPRestInterface
    @Inject lateinit var authorTableDao: AuthorTableDao

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()
        this.stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("*****PostDataService is running*****")

        addPostData(postTableDao,authorTableDao,wpRestInterface)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("*****PostDataService is stopped*****")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}