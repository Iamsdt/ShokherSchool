package com.iamsdt.shokherschool.paged

import android.arch.paging.DataSource
import android.arch.paging.TiledDataSource
import com.iamsdt.shokherschool.utilities.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 12:05 AM
 */
abstract class KitsuLimitOffsetNetworkDataSource<T> protected constructor(
        val dataProvider: KitsuRestApi) : TiledDataSource<T>() {

    var queryFilter: String = "2017-01-26T23:32:00"

    override fun countItems(): Int = DataSource.COUNT_UNDEFINED

    protected abstract fun convertToItems(items: PostData, size: Int): List<T>

    override fun loadRange(startPosition: Int, loadCount: Int): List<T>? {

        var list:List<T> ?= null

        try {
            var responseP:PostData ?= null

                    KitsuRestApi.getKitsu(queryFilter, startPosition, loadCount)
                    .enqueue(object : Callback<PostData> {
                        override fun onFailure(call: Call<PostData>?, t: Throwable?) {
                            val c = call.toString()
                            Utility.logger(message = "failed",tag = c,throwable = t)
                        }

                        override fun onResponse(call: Call<PostData>?, response: Response<PostData>?) {

                            if (response!!.isSuccessful){
                                responseP = response.body()
                            }
                        }
                    })
            list = convertToItems(responseP!!, responseP!!.data.size)
        } catch (e:Exception){
            Utility.logger(message = "response error",throwable = e)
        }
        return list
    }
}