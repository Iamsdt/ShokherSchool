package com.iamsdt.shokherschool.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.model.ThemesContract
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.ui.adapter.ClickListener
import com.iamsdt.shokherschool.ui.adapter.ColorAdapter
import kotlinx.android.synthetic.main.activity_color.*
import kotlinx.android.synthetic.main.content_color.*


class ColorActivity : AppCompatActivity(),ClickListener {

    private val themes = ArrayList<ThemesContract>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_color)

        setSupportActionBar(color_toolbar)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        color_recyclerView.layoutManager = manager

        //fill theme ids
        fillThemeIds()
        //adapter
        val adapter = ColorAdapter(themes,this,this)

        color_recyclerView.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //buy calling android.R.id.home
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(themeID: Int) {
        val themeCont = themes[themeID]

        val preferences = getSharedPreferences(ConstantUtil.colorSp, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putInt(ConstantUtil.themeKey, themeCont.id)
        editor.apply()

        val restartIntent = Intent(this, ColorActivity::class.java)
        setResult(Activity.RESULT_OK)
        finish()
        startActivity(restartIntent)
        overridePendingTransition(0, 0)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ColorActivity::class.java)
        }
    }

}
