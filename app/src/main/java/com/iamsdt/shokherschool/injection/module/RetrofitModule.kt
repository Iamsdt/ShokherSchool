package com.iamsdt.shokherschool.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import com.iamsdt.shokherschool.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.utilities.ConstantUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:08 PM
 */
@Module(includes = [NetworkModule::class])
class RetrofitModule {

    @Provides
    @ApplicationScope
    fun getRetHandler(wpRestInterface: WPRestInterface):RetrofitHandler
            = RetrofitHandler(wpRestInterface)

    @Provides
    @ApplicationScope
    fun getWPRestInterface(retrofit: Retrofit): WPRestInterface =
            retrofit.create(WPRestInterface::class.java)

    @Provides
    @ApplicationScope
    fun getRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl(ConstantUtil.retrofitBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @ApplicationScope
    fun getGson():Gson = GsonBuilder().create()
}