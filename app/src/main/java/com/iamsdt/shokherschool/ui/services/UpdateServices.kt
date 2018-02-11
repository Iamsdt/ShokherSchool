package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.data.model.EventMessage
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.utilities.ConstantUtil.Companion.ERROR
import com.iamsdt.shokherschool.data.utilities.ConstantUtil.Companion.UPDATE_SERVICE
import com.iamsdt.shokherschool.ui.base.BaseServices
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addCategoriesData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPageData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPostData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addTagData
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Shudipto Trafder on 2/11/2018.
 * at 10:08 AM
 */
class UpdateServices:BaseServices(){

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var authorTableDao: AuthorTableDao
    @Inject lateinit var categoriesTableDao: CategoriesTableDao
    @Inject lateinit var tagTableDao: TagTableDao
    @Inject lateinit var pageTableDao: PageTableDao

    @Inject lateinit var wpRestInterface: WPRestInterface

    @Inject lateinit var eventBus: EventBus


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()
        this.stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("*****UpdateServices is running *****")

        var error = ""

        //add post data
        val postMap = addPostData(postTableDao,authorTableDao,wpRestInterface)

        if (postMap.containsKey(ERROR)) {
            error = postMap[ERROR] ?: ""
        }

        //add categories and tags and page
        //add tag data
        val tagMap = addTagData(tagTableDao, wpRestInterface)
        if (tagMap.containsKey(ERROR)) {
            error += tagMap[ERROR]
        }

        //add categories
        val categoryMap = addCategoriesData(categoriesTableDao, wpRestInterface)
        if (categoryMap.containsKey(ERROR)) {
            error += categoryMap[ERROR]
        }

        //add page data
        val pageMap = addPageData(pageTableDao, wpRestInterface)
        if (pageMap.containsKey(ERROR)) {
            error += pageMap[ERROR]
        }

        eventBus.post(EventMessage(key = UPDATE_SERVICE,
                message = "Update Service complete",errorMessage = error))

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("*****UpdateServices is stopped*****")
    }
}