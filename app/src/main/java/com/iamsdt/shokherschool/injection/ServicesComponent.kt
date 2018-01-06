package com.iamsdt.shokherschool.injection

import com.iamsdt.shokherschool.injection.module.ServiceModule
import com.iamsdt.shokherschool.injection.scopes.ServicesScope
import com.iamsdt.shokherschool.ui.services.DataInsertService
import dagger.Component

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:17 AM
 */
@ServicesScope
@Component(modules = [ServiceModule::class],
        dependencies = [MyApplicationComponent::class])
interface ServicesComponent {
    fun inject(dataInsertService: DataInsertService)
}