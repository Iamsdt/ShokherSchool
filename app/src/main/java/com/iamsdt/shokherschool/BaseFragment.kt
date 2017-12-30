package com.iamsdt.shokherschool

import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import com.iamsdt.shokherschool.injection.ActivityComponent
import com.iamsdt.shokherschool.injection.DaggerActivityComponent
import com.iamsdt.shokherschool.injection.module.ActivityModule

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:31 PM
 */
open class BaseFragment : Fragment() {

    @UiThread
    fun getComponent(): ActivityComponent =
            DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(activity))
                    .myApplicationComponent(MyApplication().get(activity).getComponent())
                    .build()
}