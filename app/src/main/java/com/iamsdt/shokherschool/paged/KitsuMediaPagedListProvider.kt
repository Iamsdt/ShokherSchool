package com.iamsdt.shokherschool.paged

import android.arch.paging.LivePagedListProvider
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.retrofit.pojo.post.Title

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 12:14 AM
 */
object KitsuMediaPagedListProvider {
    private val dataSource = object: KitsuLimitOffsetNetworkDataSource<PostResponse>(KitsuRestApi) {
        override fun convertToItems(items: PostData, size: Int): List<PostResponse> {
            return List(size, { index ->
                items.data.elementAtOrElse(index, { PostResponse("2017-01-26T23:32:00", 1,
                        "", Title(),"",0,1,null)})
            })
        }
    }

    fun allKitsu(): LivePagedListProvider<Int, PostResponse> {
        return object : LivePagedListProvider<Int, PostResponse>() {
            override fun createDataSource(): KitsuLimitOffsetNetworkDataSource<PostResponse> = dataSource
        }
    }

    fun setQueryFilter(queryFilter: String) {
        dataSource.queryFilter = queryFilter
    }
}