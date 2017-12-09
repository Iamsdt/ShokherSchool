package com.iamsdt.shokherschool.utilities

import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.util.Log
import com.iamsdt.shokherschool.BuildConfig
import com.iamsdt.shokherschool.R


/**
* Created by Shudipto Trafder on 11/14/2017.
*/

class Utility{

    companion object {

        /**Show log message in the debug mode
         * @param message log message
         * @param tag log tag
         * @param throwable the error that throw*/
        fun logger(message:String,
                   tag:String = "custom",
                   throwable: Throwable? = null){

            if (BuildConfig.DEBUG){
                Log.e(tag,message,throwable)
            }
        }



        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info: NetworkInfo = manager.activeNetworkInfo

            return info.isConnectedOrConnecting
        }

        fun customTab(context: Context,link:String){
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(R.attr.colorPrimary)
            builder.setShowTitle(false)
            builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                    context.resources, R.drawable.dialog_back))
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(link))
        }
    }
}