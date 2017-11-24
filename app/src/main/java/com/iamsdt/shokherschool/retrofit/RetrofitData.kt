package com.iamsdt.shokherschool.retrofit

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
    var dataResponse:DataResponse ?= null

    init {
        if (instance == null){

            val okHttpClient = OkHttpClient().newBuilder()
                    .readTimeout(1,TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES).build()

            instance = Retrofit.Builder()
                    .baseUrl(DataResponse.retrofitBaseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        dataResponse = instance!!.create(DataResponse::class.java)
    }
}