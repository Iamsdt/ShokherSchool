package com.iamsdt.shokherschool.retrofit

import retrofit2.Retrofit


/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 7:23 PM
 */
class RetrofitData {

    var instance:Retrofit ?= null
    var wpRestInterface: WPRestInterface ?= null

    init {
        if (instance == null){

//            instance = Retrofit.Builder()
//                    .baseUrl(ConstantUtil.retrofitBaseUrl)
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
        }

        wpRestInterface = instance!!.create(WPRestInterface::class.java)
    }
}