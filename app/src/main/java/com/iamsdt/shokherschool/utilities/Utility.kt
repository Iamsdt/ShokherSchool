package com.iamsdt.shokherschool.utilities

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.iamsdt.shokherschool.R


/**
* Created by Shudipto Trafder on 11/14/2017.
*/

class Utility{

    companion object {

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