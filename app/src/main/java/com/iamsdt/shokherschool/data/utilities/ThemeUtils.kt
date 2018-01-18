package com.iamsdt.shokherschool.data.utilities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.iamsdt.shokherschool.R

/**
 * Created by Shudipto Trafder on 1/18/2018.
 * at 9:05 PM
 */
class ThemeUtils {

    companion object

    /**
     * This methods for select theme from
     * shared preference that saved in color activity
     *
     * @param activity to select theme
     */

    fun initialize(activity: Activity) {
        val sp: SharedPreferences = activity.getSharedPreferences(ConstantUtil.colorSp,
                Context.MODE_PRIVATE)

        val id = sp.getInt(ConstantUtil.themeKey, R.style.AppTheme_NoActionBar)

        activity.setTheme(id)
    }
}
}