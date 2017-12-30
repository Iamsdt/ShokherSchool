package com.iamsdt.shokherschool.injection.module

import android.content.Context
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 9:39 PM
 */

@Module
class ContextModule(context:Context){
    private val mContext = context

    @Provides
    @ApplicationScope
    fun getContext(): Context = mContext
}