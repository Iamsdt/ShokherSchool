package com.iamsdt.shokherschool.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import androidx.content.edit
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.model.ThemesContract
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.ui.adapter.ClickListener
import com.iamsdt.shokherschool.ui.adapter.ColorAdapter
import kotlinx.android.synthetic.main.activity_color.*
import kotlinx.android.synthetic.main.content_color.*


class ColorActivity : AppCompatActivity(), ClickListener {

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
        val adapter = ColorAdapter(themes, this, this)

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

    override fun onItemClick(themeID: Int) {
        val themeCont = themes[themeID]

        val sp = getSharedPreferences(ConstantUtil.colorSp, Context.MODE_PRIVATE)

        sp.edit { putInt(ConstantUtil.themeKey, themeCont.id) }

        restartActivity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            //setResult(Activity.RESULT_OK)
            finish()

        } else if (id == R.id.nightMode && showNightModeIcon) {

            nightModeStatus = if (nightModeStatus) {
                //night mode on
                //now off night mode
                turnOffNightMode(this@ColorActivity)
                setResult(Activity.RESULT_OK)
                restartActivity()
                false
            } else {
                //night mode false
                //now on night mode
                turnOnNightMode(this@ColorActivity)
                restartActivity()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if (showNightModeIcon) {
            menuInflater.inflate(R.menu.color, menu)

            val nightMode = menu?.findItem(R.id.nightMode)

            if (nightModeStatus) {
                nightMode?.setIcon(R.drawable.ic_half_moon)
            } else {
                nightMode?.setIcon(R.drawable.ic_wb_sunny_black_24dp)
            }
        }

        return true
    }

    private fun restartActivity() {
        val restartIntent = Intent(this@ColorActivity, ColorActivity::class.java)
        setResult(Activity.RESULT_OK)

        finish()

        startActivity(restartIntent)
        overridePendingTransition(0, 0)
    }

    companion object {
        private var nightModeStatus = false

        private var showNightModeIcon = true

        /**
         * Create Intent for launcher class
         * @param context of the class
         * @return Intent
         */
        fun createIntent(context: Context): Intent {
            return Intent(context, ColorActivity::class.java)
        }


        /**
         * Method for turn on night mode
         * this only change the value of sp
         * @param context for access sp
         */
        private fun turnOnNightMode(context: Context) {
            val sp = context.getSharedPreferences(ConstantUtil.NIGHT_MODE_SP_KEY, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean(ConstantUtil.NIGHT_MODE_VALUE_KEY, true)
            editor.apply()
        }

        /**
         * Method for turn on night mode
         * this only change the value of sp
         * @param context for access sp
         */
        private fun turnOffNightMode(context: Context) {
            val sp = context.getSharedPreferences(ConstantUtil.NIGHT_MODE_SP_KEY, Context.MODE_PRIVATE)

            val editor = sp.edit()
            editor.putBoolean(ConstantUtil.NIGHT_MODE_VALUE_KEY, false)
            editor.apply()
        }

    }

}
