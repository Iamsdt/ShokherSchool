package com.iamsdt.shokherschool.data.utilities

import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.iamsdt.shokherschool.R
import java.text.SimpleDateFormat
import java.util.*


/**
* Created by Shudipto Trafder on 11/14/2017.
* at 9:01 PM
*/

class Utility {

    companion object {

        /**
         * Checking network is available or not
         * @return network state
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val manager: ConnectivityManager? = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info: NetworkInfo? = manager?.activeNetworkInfo

            return info != null && info.isConnectedOrConnecting
        }

        fun isTimeForRunService(context: Context): Boolean {
            val spDate = MyDateUtil.getDateForService(context)

            // if previous date is empty
            // then time for running service
            if (spDate.isEmpty()) {
                return true
            }

            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
            val previousDate = sdf.parse(spDate)

            //fixme 1/10/2018 add setting for sync interval
            val interval = 7

            val s = previousDate.before(previousDate)

            return false
        }

        /**
         * This method for showing external link on
         * a custom tab
         * **/
        fun customTab(context: Context, link: String) {
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