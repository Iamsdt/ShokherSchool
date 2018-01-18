package com.iamsdt.shokherschool.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.model.ThemesContract



class ColorActivity : AppCompatActivity() {

    private val themes = ArrayList<ThemesContract>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
    }

    /**
     * This method is for add new theme in arrayList
     * array list contain theme name and it's id
     */

    private fun fillThemeIds() {
        //fill array with styles ids
        themes.add(ThemesContract("Default", R.style.AppTheme_NoActionBar))
        themes.add(ThemesContract("Amber", R.style.amber_dark))
        themes.add(ThemesContract("Purple", R.style.purple_dark))
        themes.add(ThemesContract("Orange", R.style.orange))
        themes.add(ThemesContract("Cyan", R.style.cyan))
        themes.add(ThemesContract("Deep Orange", R.style.deeporange))
        themes.add(ThemesContract("Green", R.style.green))
    }

}
