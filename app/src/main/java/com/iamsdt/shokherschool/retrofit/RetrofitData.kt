package com.iamsdt.shokherschool.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 7:23 PM
 */
class RetrofitData {

    var instance:Retrofit ?= null
    var dataResponse:DataResponse ?= null

    init {
        if (instance == null){
            instance = Retrofit.Builder()
                    .baseUrl(DataResponse.retrofitBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        dataResponse = instance!!.create(DataResponse::class.java)
    }
}