package com.iamsdt.shokherschool.ui.services

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PageTableDao
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.ui.base.BaseServices
import javax.inject.Inject

@SuppressLint("Registered")
/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:10 AM
 */
class DataInsertService:BaseServices(){

    @Inject lateinit var pageTableDao:PageTableDao
    @Inject lateinit var categoriesTableDao:CategoriesTableDao
    @Inject lateinit var wpRestInterface:WPRestInterface


    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        AsyncTask.execute({

        })


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}