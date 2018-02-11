package com.iamsdt.shokherschool.data.utilities

import android.content.Context
import androidx.content.edit
import com.iamsdt.shokherschool.data.utilities.ConstantUtil.Companion.APP_RUN_FOR_FIRST_TIME
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Shudipto Trafder on 2/10/2018.
 * at 11:22 PM
 */
class SpUtils {
    companion object {

        /**Get date from sp
         * @param context access to sp
         * @return date that saved in the sp*/
        fun getPostDateFromSp(context: Context): String {
            val sp = context.getSharedPreferences(ConstantUtil.dateSpName,
                    Context.MODE_PRIVATE)
            return sp.getString(ConstantUtil.dateSpName, ConstantUtil.dateSpDefaultValue)
        }

        /**Save date to sp
         * @param context for access sp
         * @param string date to save*/
        fun setPostDateOnSp(context: Context, string: String) {
            val sp = context.getSharedPreferences(ConstantUtil.dateSpName,
                    Context.MODE_PRIVATE)

            sp.edit {
                putString(ConstantUtil.dateSpName, string)
            }
        }

        /** Get date from sp
         * @return date*/
        fun getDateForUpdateService(context: Context): String {
            val sp = context.getSharedPreferences(ConstantUtil.ServiceSp,
                    Context.MODE_PRIVATE)
            //default value is empty
            //complete 1/10/2018 change default value to empty
            return sp.getString(ConstantUtil.ServiceRunningDate,
                    "")
        }

        fun saveUpdateServiceDateOnSp(context: Context) {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            val todayDate = sdf.format(Date())
            val sp = context.getSharedPreferences(ConstantUtil.ServiceSp,
                    Context.MODE_PRIVATE)

            sp.edit {
                putString(ConstantUtil.ServiceRunningDate, todayDate)
            }
        }

        //app running for first time
        fun isAppRunFirstTime(context: Context): Boolean {
            val sp = context.getSharedPreferences(APP_RUN_FOR_FIRST_TIME, Context.MODE_PRIVATE)
            return sp.getBoolean(APP_RUN_FOR_FIRST_TIME, true)
        }

        fun saveAppRunFirstTime(context: Context) {
            val sp = context.getSharedPreferences(APP_RUN_FOR_FIRST_TIME, Context.MODE_PRIVATE)
            sp.edit { putBoolean(APP_RUN_FOR_FIRST_TIME, false) }
        }
    }
}