package com.iamsdt.shokherschool

import android.app.Application
import com.iamsdt.shokherschool.injection.DaggerMyApplicationComponent
import com.iamsdt.shokherschool.injection.MyApplicationComponent
import com.iamsdt.shokherschool.injection.module.ContextModule
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.base.BaseFragment
import com.iamsdt.shokherschool.ui.base.BaseServices
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

    fun get(activity: BaseActivity): MyApplication
            = activity.application as MyApplication

    fun get(fragment: BaseFragment): MyApplication
            = fragment.activity?.application as MyApplication

    fun get(context:BaseServices):MyApplication =
            context.applicationContext as MyApplication

}