package com.iamsdt.shokherschool

import android.annotation.SuppressLint
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import com.iamsdt.shokherschool.injection.ActivityComponent

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 11:29 PM
 */

@SuppressLint("Registered")
class BaseActivity:AppCompatActivity(){

    @UiThread
    fun getComponent():ActivityComponent? = null

}