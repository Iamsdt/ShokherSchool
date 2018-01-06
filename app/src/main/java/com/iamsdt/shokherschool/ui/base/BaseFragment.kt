package com.iamsdt.shokherschool.ui.base

import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import com.iamsdt.shokherschool.injection.ActivityComponent
import com.iamsdt.shokherschool.injection.DaggerActivityComponent

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:31 PM
 */
open class BaseFragment : Fragment() {

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): ActivityComponent =
            DaggerActivityComponent.builder()
                    //.activityModule(ActivityModule(activity!!.parent))
                    //.myApplicationComponent(MyApplication().get(activity!!.parent).getComponent())
                    .build()
}