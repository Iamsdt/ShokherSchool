package com.iamsdt.shokherschool

import android.app.Activity
import android.app.Application
import com.iamsdt.shokherschool.injection.DaggerMyApplicationComponent
import com.iamsdt.shokherschool.injection.MyApplicationComponent
import com.iamsdt.shokherschool.injection.module.ContextModule
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:25 PM
 */
class MyApplication : Application() {

    private var dagger: MyApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        //planting timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.asTree())
        }

        dagger = DaggerMyApplicationComponent.builder()
                .contextModule(ContextModule(this)).build()

    }

    fun getComponent() = dagger

    fun get(activity: Activity): MyApplication
            = activity.application as MyApplication

}