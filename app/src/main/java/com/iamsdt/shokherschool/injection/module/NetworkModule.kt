package com.iamsdt.shokherschool.injection.module

import android.content.Context
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:15 PM
 */

@Module(includes = [ContextModule::class])
class NetworkModule {

    @Provides
    @ApplicationScope
    fun getClient(cache: Cache): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES).build()
    }

    @Provides
    @ApplicationScope
    fun getCache(file: File): Cache = Cache(file, 10 * 1024 * 1024)

    @Provides
    @ApplicationScope
    fun getFile(context: Context): File = File(context.cacheDir, "okHttp")
}