package com.iamsdt.shokherschool.injection.module

import android.content.Context
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 10:55 PM
 */

@Module(includes = [ContextModule::class,NetworkModule::class])
class PicassoModule{
    @Provides
    @ApplicationScope
    fun getPicasso(context: Context,client: OkHttpClient): Picasso
            = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(client))
            .build()
}