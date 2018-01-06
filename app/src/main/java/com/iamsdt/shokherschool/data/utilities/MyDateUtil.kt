package com.iamsdt.shokherschool.data.utilities

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Shudipto Trafder on 12/9/2017.
 * at 12:21 PM
 */
class MyDateUtil{
    companion object {

        fun getReadableDate(string: String): String {

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US)
            val output = SimpleDateFormat("dd-MMM-yyyy",Locale.US)
            val postDate = sdf.parse(string)

            return output.format(postDate)
        }

        /**Get date from sp
         * @param context access to sp
         * @return date that saved in the sp*/
        fun getDate(context: Context):String {
            val sp = context.getSharedPreferences(ConstantUtil.dateSpName,
                    Context.MODE_PRIVATE)
            return sp.getString(ConstantUtil.dateSpName,ConstantUtil.dateSpDefaultValue)
        }

        /**Save date to sp
         * @param context for access sp
         * @param string date to save*/
        fun setDateOnSp(context: Context,string: String){
            val sp = context.getSharedPreferences(ConstantUtil.dateSpName,
                    Context.MODE_PRIVATE)

            val editor = sp.edit()
            editor.putString(ConstantUtil.dateSpName,string)
            editor.apply()
        }

        /**
         * This methods for compare two date
         * to find out which date is older
         * @param first is consider as a first date
         * @param second is consider as a second date
         * @return the date older date
         * */
        fun compareTwoDate(first: Date, second: Date): Date
                = if (first.before(second)) {
            first
        } else {
            second
        }
    }
}