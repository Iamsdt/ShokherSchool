package com.iamsdt.shokherschool.ui.base

import android.annotation.SuppressLint
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import com.iamsdt.shokherschool.MyApplication
import com.iamsdt.shokherschool.injection.ActivityComponent
import com.iamsdt.shokherschool.injection.DaggerActivityComponent
import com.iamsdt.shokherschool.injection.module.ActivityModule

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:29 PM
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): ActivityComponent =
            DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .myApplicationComponent(MyApplication().get(this).getComponent())
                    .build()

}