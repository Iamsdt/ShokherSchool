package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PageTableDao
import com.iamsdt.shokherschool.data.database.dao.TagTableDao
import com.iamsdt.shokherschool.data.model.EventMessage
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.data.utilities.ConstantUtil.Companion.DATA_INSERT_SERVICE
import com.iamsdt.shokherschool.ui.base.BaseServices
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addCategoriesData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPageData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addTagData
import org.greenrobot.eventbus.EventBus
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

        var error = ""

        //add categories and tags and page
        //add tag data
        addTagData(tagTableDao, wpRestInterface,eventBus,true)

        //add categories
        val categoryMap = addCategoriesData(categoriesTableDao, wpRestInterface)
        if (categoryMap.containsKey(ConstantUtil.ERROR)) {
            error += categoryMap[ConstantUtil.ERROR]
        }

        //add page data
        val pageMap = addPageData(pageTableDao, wpRestInterface)
        if (pageMap.containsKey(ConstantUtil.ERROR)) {
            error += pageMap[ConstantUtil.ERROR]
        }

        eventBus.post(EventMessage(key = DATA_INSERT_SERVICE,
                message = "complete",errorMessage = error))

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