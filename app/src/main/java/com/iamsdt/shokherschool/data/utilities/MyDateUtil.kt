package com.iamsdt.shokherschool.data.utilities

import org.joda.time.DateTime
import org.joda.time.Days
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Shudipto Trafder on 12/9/2017.
 * at 12:21 PM
 */
class MyDateUtil{
    companion object {

        fun getReadableDate(string: String?): String {

            if (string == null || string.isEmpty()){
                return "no date found"
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US)
            val output = SimpleDateFormat("dd-MMM-yyyy",Locale.US)
            val postDate = sdf.parse(string)

            return output.format(postDate)
        }

        /**
         * This methods for compare two date
         * to find out which date is older
         * @param first is consider as a first date
         * @param second is consider as a second date
         * @return older date
         * */
        fun compareTwoDate(first: Date, second: Date): Date
                = if (first.before(second)) {
            first
        } else {
            second
        }

        /**
         * Get difference between toady and provided
         * @param spDate given date
         *
         * @return number of difference
         */
        fun compareDateIntervals(spDate:String):Int{
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            val previousDate = sdf.parse(spDate)

            val today = DateTime(Date())
            val preDate = DateTime(previousDate)

            val day = Days.daysBetween(preDate,today).days
            Timber.i(day.toString())

            return day
        }
    }
}