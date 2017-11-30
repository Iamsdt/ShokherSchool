package com.iamsdt.shokherschool.paged

import com.iamsdt.shokherschool.retrofit.WPRestInterface
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 12:06 AM
 */
object KitsuRestApi {
    private val KITSU_API: WPRestInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://shokherschool.com/wp-json/wp/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        KITSU_API = retrofit.create(WPRestInterface::class.java)
    }

    fun getKitsu(filter: String, offset: Int, limit: Int): Call<PostData> {
        return null!!
    }
}