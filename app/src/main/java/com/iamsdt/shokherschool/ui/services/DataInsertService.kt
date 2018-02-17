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
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:10 AM
 */
class DataInsertService : BaseServices() {

    @Inject lateinit var pageTableDao: PageTableDao
    @Inject lateinit var categoriesTableDao: CategoriesTableDao
    @Inject lateinit var tagTableDao: TagTableDao
    @Inject @Named("detailsRest") lateinit var wpRestInterface: WPRestInterface

    @Inject lateinit var eventBus: EventBus

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()
        isRunning = true
        //this.stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("*****DataInsertService is running*****")

        isRunning = true

        //add categories and tags and page
        //add tag data
        addTagData(tagTableDao, wpRestInterface,true,this.baseContext)

        //add categories
        addCategoriesData(categoriesTableDao, wpRestInterface,this.baseContext,true)

        //add page data
        addPageData(pageTableDao, wpRestInterface,this.baseContext,true)

        Timber.i("DataInsertService complete")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("*****DataInsertService is stopped*****")
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        var isRunning = false
    }

}