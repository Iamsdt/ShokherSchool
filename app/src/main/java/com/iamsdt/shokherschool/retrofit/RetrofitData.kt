package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.utilities.ConstantUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 7:23 PM
 */
class RetrofitData {

    var instance:Retrofit ?= null
    var wpRestInterface: WPRestInterface ?= null

    init {
        if (instance == null){

            val okHttpClient = OkHttpClient().newBuilder()
                    .readTimeout(1,TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES).build()

            instance = Retrofit.Builder()
                    .baseUrl(ConstantUtil.retrofitBaseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        wpRestInterface = instance!!.create(WPRestInterface::class.java)
    }
}