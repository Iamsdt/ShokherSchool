package com.iamsdt.shokherschool.injection.module

import am.appwise.components.ni.NoInternetDialog
import android.app.Activity
import com.iamsdt.shokherschool.adapter.MainAdapter
import com.iamsdt.shokherschool.injection.scopes.ActivityScope
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 10:58 PM
 */

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityScope
    fun getAdapter(picasso: Picasso): MainAdapter
            = MainAdapter(picasso, activity)

    @Provides
    @ActivityScope
    fun getNoInternetDialog(): NoInternetDialog
            = NoInternetDialog.Builder(activity.baseContext).build()
}
