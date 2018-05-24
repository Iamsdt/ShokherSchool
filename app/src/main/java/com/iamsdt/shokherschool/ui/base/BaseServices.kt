package com.iamsdt.shokherschool.ui.base

import android.annotation.SuppressLint
import android.app.Service
import android.support.annotation.UiThread
import com.iamsdt.shokherschool.MyApplication
import com.iamsdt.shokherschool.injection.DaggerServicesComponent
import com.iamsdt.shokherschool.injection.ServicesComponent
import com.iamsdt.shokherschool.injection.module.ServiceModule

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:13 AM
 */
@SuppressLint("Registered")
abstract class BaseServices:Service(){

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): ServicesComponent =
            DaggerServicesComponent.builder().serviceModule(ServiceModule(this))
                    .myApplicationComponent(MyApplication().get(this).getComponent())
                    .build()

}