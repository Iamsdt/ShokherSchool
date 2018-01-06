package com.iamsdt.shokherschool.injection.module

import com.iamsdt.shokherschool.injection.scopes.ActivityScope
import com.iamsdt.shokherschool.ui.adapter.MainAdapter
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 10:58 PM
 */

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    fun getAdapter(picasso: Picasso): MainAdapter
            = MainAdapter(picasso, activity)
}
