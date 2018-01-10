package com.iamsdt.shokherschool.data.utilities

import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.iamsdt.shokherschool.R


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
                return isNetworkAvailable(context)
            }

            //fixme 1/10/2018 add setting for sync interval
            val interval = 7

            val intervalDay = MyDateUtil.compareDateIntervals(spDate)

            return (intervalDay >= interval) && isNetworkAvailable(context)
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