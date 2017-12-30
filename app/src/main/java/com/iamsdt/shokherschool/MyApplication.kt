package com.iamsdt.shokherschool

import android.app.Activity
import android.app.Application
import com.iamsdt.shokherschool.injection.MyApplicationComponent
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:25 PM
 */
class MyApplication:Application(){

    private val dagger:MyApplicationComponent ?= null

    override fun onCreate() {
        super.onCreate()

        //planting timber
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{
            Timber.plant(Timber.asTree())
        }



    }

    fun getComponent() = dagger

    fun get(activity: Activity):MyApplication
            = activity.application as MyApplication

}