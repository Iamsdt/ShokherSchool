package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.ui.base.BaseServices
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addCategoriesData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPageData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPostData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addTagData
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
        //add post data
        addPostData(postTableDao,authorTableDao,wpRestInterface)
        //add categories and tags and page

        //add tag data
        addTagData(tagTableDao, wpRestInterface)

        //add categories
        addCategoriesData(categoriesTableDao, wpRestInterface)

        //add page data
        addPageData(pageTableDao, wpRestInterface)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("*****UpdateServices is stopped*****")
    }
}