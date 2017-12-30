package com.iamsdt.shokherschool.injection.module

import android.content.Context
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 10:55 PM
 */

@Module(includes = [ContextModule::class,NetworkModule::class])
class PicassoModule{
    @Provides
    @ApplicationScope
    fun getPicasso(context: Context,downloader: OkHttpDownloader): Picasso
            = Picasso.Builder(context)
            .downloader(downloader)
            .build()
}