package com.iamsdt.shokherschool.paged

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 12:12 AM
 */
class KitsuViewModel(app: Application) : AndroidViewModel(app) {

    private var allKitsuLiveData: LiveData<PagedList<PostResponse>>? = null

    val allKitsu: LiveData<PagedList<PostResponse>>
        get() {
            if (null == allKitsuLiveData) {
                allKitsuLiveData = KitsuMediaPagedListProvider.allKitsu().create(0,
                        PagedList.Config.Builder()
                                .setPageSize(PAGED_LIST_PAGE_SIZE)
                                .setInitialLoadSizeHint(PAGED_LIST_PAGE_SIZE)
                                .setEnablePlaceholders(PAGED_LIST_ENABLE_PLACEHOLDERS)
                                .build())
            }
            return allKitsuLiveData ?: throw AssertionError("Check your threads ...")
        }

    fun setQueryFilter(queryFilter: String) {
        KitsuMediaPagedListProvider.setQueryFilter(queryFilter)
        allKitsuLiveData = null // invalidate
    }

    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 10
        private const val PAGED_LIST_ENABLE_PLACEHOLDERS = false
    }
}