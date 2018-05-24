package com.iamsdt.shokherschool.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:08 PM
 */
@Module(includes = [NetworkModule::class])
class RetrofitModule {

//    @Provides
//    @ApplicationScope
//    fun getRetHandler(wpRestInterface: WPRestInterface):RetrofitHandler
//            = RetrofitHandler(wpRestInterface)

    @Provides
    @ApplicationScope
    fun getWPRestInterface(@Named("post") retrofit: Retrofit): WPRestInterface =
            retrofit.create(WPRestInterface::class.java)

    @Provides
    @ApplicationScope
    @Named("detailsRest")
    fun getWPRestInterfaceDetails(@Named("details") retrofit: Retrofit): WPRestInterface =
            retrofit.create(WPRestInterface::class.java)

    @Provides
    @ApplicationScope
    @Named("post")
    fun getRetrofitPost(okHttpClient: OkHttpClient,gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl(ConstantUtil.retrofitBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @ApplicationScope
    @Named("details")
    fun getRetrofitDetails(okHttpClient: OkHttpClient,gson: Gson): Retrofit
            = Retrofit.Builder()
            .baseUrl(ConstantUtil.retrofitBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Provides
    @ApplicationScope
    fun getGson():Gson = GsonBuilder().create()
}