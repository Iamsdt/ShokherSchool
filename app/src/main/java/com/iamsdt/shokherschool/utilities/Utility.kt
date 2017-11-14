package com.iamsdt.shokherschool.utilities

import android.util.Log
import com.iamsdt.shokherschool.BuildConfig

/**
* Created by Shudipto Trafder Trafder on 11/14/2017.
*/

class Utility{

    companion object {

        fun logger(message:String,
                   tag:String = "custom",
                   throwable: Throwable? = null){

            if (BuildConfig.DEBUG){
                Log.e(tag,message,throwable)
            }

        }
    }
}