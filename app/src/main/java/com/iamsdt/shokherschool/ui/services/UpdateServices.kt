package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.utilities.SpUtils
import com.iamsdt.shokherschool.ui.base.BaseServices
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addCategoriesData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPageData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addPostData
import com.iamsdt.shokherschool.ui.services.ServiceUtils.Companion.addTagData
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

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

    @Inject @Named("detailsRest") lateinit var wpRestInterface: WPRestInterface


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()

        isRunning = true

        this.stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("*****UpdateServices is running *****")

        isRunning = true

        //add post data
        addPostData(postTableDao,authorTableDao,wpRestInterface,null,false,
                this.baseContext)

        //add categories and tags and page
        //add tag data
        addTagData(tagTableDao, wpRestInterface,true,this.baseContext)

        //add categories
        addCategoriesData(categoriesTableDao, wpRestInterface,this.baseContext,true)

        //add page data
        addPageData(pageTableDao, wpRestInterface,this.baseContext,true)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("*****UpdateServices is stopped*****")
        isRunning = false

        SpUtils.saveUpdateServiceDateOnSp(this.baseContext)
    }

    companion object {
        var isRunning = false
    }
}