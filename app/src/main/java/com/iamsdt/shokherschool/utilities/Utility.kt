package com.iamsdt.shokherschool.utilities

import android.content.Context
import android.util.Log
import com.iamsdt.shokherschool.BuildConfig



/**
* Created by Shudipto Trafder on 11/14/2017.
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

        fun getDate(context: Context):String {
            val sp = context.getSharedPreferences(ConstantUtil.dateSpName,
                    Context.MODE_PRIVATE)
            return sp.getString(ConstantUtil.dateSpName,ConstantUtil.dateSpDefaultValue)
        }

        fun setDateOnSp(context: Context,string: String){
            val sp = context.getSharedPreferences(ConstantUtil.dateSpName,
                    Context.MODE_PRIVATE)

            val editor = sp.edit()
            editor.putString(ConstantUtil.dateSpName,string)
            editor.apply()
        }
    }
}