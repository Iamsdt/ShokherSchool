package com.iamsdt.shokherschool.ui.base

import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import com.iamsdt.shokherschool.MyApplication
import com.iamsdt.shokherschool.injection.DaggerFragmentComponent
import com.iamsdt.shokherschool.injection.FragmentComponent
import com.iamsdt.shokherschool.injection.module.FragmentModule

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:31 PM
 */
open class BaseFragment : Fragment() {

    @Suppress("DEPRECATION")
    @UiThread
    fun getComponent(): FragmentComponent =
            DaggerFragmentComponent.builder()
                    .fragmentModule(FragmentModule(this))
                    .myApplicationComponent(MyApplication().get(this).getComponent())
                    .build()
}