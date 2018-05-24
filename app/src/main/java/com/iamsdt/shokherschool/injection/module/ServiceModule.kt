package com.iamsdt.shokherschool.injection.module

import com.iamsdt.shokherschool.injection.scopes.ServicesScope
import com.iamsdt.shokherschool.ui.base.BaseServices
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:12 AM
 */
@Module
class ServiceModule(private val baseServices: BaseServices){

    @Provides
    @ServicesScope
    fun getServices(): BaseServices
            = baseServices
}