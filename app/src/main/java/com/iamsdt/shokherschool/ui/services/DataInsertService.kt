package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PageTableDao
import com.iamsdt.shokherschool.data.database.dao.TagTableDao
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.ui.base.BaseServices
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addCategoriesData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPageData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addTagData
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:10 AM
 */
class DataInsertService : BaseServices() {

    @Inject lateinit var pageTableDao: PageTableDao
    @Inject lateinit var categoriesTableDao: CategoriesTableDao
    @Inject lateinit var tagTableDao: TagTableDao
    @Inject lateinit var wpRestInterface: WPRestInterface

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()
        this.stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //add tag data
        addTagData(tagTableDao,wpRestInterface)

        //add categories
        addCategoriesData(categoriesTableDao,wpRestInterface)

        //add page data
        addPageData(pageTableDao,wpRestInterface)

        Timber.i("*****DataInsertService is running*****")
        Timber.i("DataInsertService complete")


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("*****DataInsertService is stopped*****")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}