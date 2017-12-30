package com.iamsdt.shokherschool

import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import com.iamsdt.shokherschool.injection.ActivityComponent

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:31 PM
 */
class BaseFragment:Fragment(){

    @UiThread
    fun getComponent(): ActivityComponent? = null
}