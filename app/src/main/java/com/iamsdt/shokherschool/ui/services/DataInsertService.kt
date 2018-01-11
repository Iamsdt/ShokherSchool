package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PageTableDao
import com.iamsdt.shokherschool.data.database.dao.TagTableDao
import com.iamsdt.shokherschool.data.database.table.CategoriesTable
import com.iamsdt.shokherschool.data.database.table.PageTable
import com.iamsdt.shokherschool.data.database.table.TagTable
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.retrofit.pojo.categories.CategoriesResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.page.PageResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.tags.TagResponse
import com.iamsdt.shokherschool.data.utilities.MyDateUtil
import com.iamsdt.shokherschool.ui.base.BaseServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    companion object {
        var isRunning = false
        private var pageInsertComplete = false
        private var categoriesInsertComplete = false
        private var tagsInsertComplete = false

        fun isServiceCompleted(): Boolean {
            return pageInsertComplete && categoriesInsertComplete && tagsInsertComplete
        }
    }

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()

        isRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val pageCall = wpRestInterface.getPages()
        pageData(pageCall)

        val categoriesCall = wpRestInterface.getCategories()
        categoriesData(categoriesCall)

        val tagCall = wpRestInterface.getTags()
        tagData(tagCall)

        Timber.i("Service complete")

        if (isServiceCompleted()) {
            Timber.i("Service complete date saved")
            MyDateUtil.saveServiceDateOnSp(baseContext)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    //insert tag data
    private fun tagData(tagCall: Call<List<TagResponse>>) {
        tagCall.enqueue(object : Callback<List<TagResponse>> {
            override fun onResponse(call: Call<List<TagResponse>>?,
                                    response: Response<List<TagResponse>>?) {

                //if something wrong a empty list will crated
                val tags = response?.body() ?: arrayListOf()

                AsyncTask.execute({

                    tags.map { TagTable(it.count, it.name, it.id) }
                            .forEach { tagTableDao.insert(it) }

                    Timber.i("category insert finished")
                    tagsInsertComplete = true

                })

            }

            override fun onFailure(call: Call<List<TagResponse>>?, t: Throwable?) {
                Timber.i(t, "tag Response error")
            }

        })
    }

    //insert categories data
    private fun categoriesData(categoriesCall: Call<List<CategoriesResponse>>) {
        categoriesCall.enqueue(object : Callback<List<CategoriesResponse>> {
            override fun onResponse(call: Call<List<CategoriesResponse>>?,
                                    response: Response<List<CategoriesResponse>>?) {

                //if something wrong a empty list will crated
                val categories = response?.body() ?: arrayListOf()

                AsyncTask.execute({

                    for (category in categories) {
                        val table = CategoriesTable(category.count, category.name,
                                category.description, category.id)

                        categoriesTableDao.insert(table)
                    }

                    Timber.i("category insert finished")
                    categoriesInsertComplete = true
                })

            }

            override fun onFailure(call: Call<List<CategoriesResponse>>?, t: Throwable?) {
                Timber.i(t, "Categories Response error")
            }

        })
    }

    //this methods for insert page data
    private fun pageData(pageCall: Call<List<PageResponse>>) {
        pageCall.enqueue(object : Callback<List<PageResponse>> {
            override fun onResponse(call: Call<List<PageResponse>>?,
                                    response: Response<List<PageResponse>>?) {

                val pages = response?.body() ?: arrayListOf()

                AsyncTask.execute({

                    for (page in pages) {
                        val table = PageTable(page.date, page.author, page.title?.rendered,
                                page.content?.rendered,
                                page.featuredMedia, page.id)

                        pageTableDao.insert(table)
                    }

                    Timber.i("page insert finished")
                    pageInsertComplete = true

                })
            }

            override fun onFailure(call: Call<List<PageResponse>>?, t: Throwable?) {
                Timber.i(t, "Categories Response error")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}