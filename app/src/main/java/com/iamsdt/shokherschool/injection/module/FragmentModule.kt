package com.iamsdt.shokherschool.injection.module

import com.iamsdt.shokherschool.injection.scopes.FragmentScope
import com.iamsdt.shokherschool.ui.base.BaseFragment
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:31 AM
 */
@Module
class FragmentModule(private val baseFragment: BaseFragment){

    @Provides
    @FragmentScope
    fun provideFragment(): BaseFragment
            = baseFragment
}