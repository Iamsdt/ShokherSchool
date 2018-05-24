package com.iamsdt.shokherschool.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.iamsdt.shokherschool.R

import kotlinx.android.synthetic.main.activity_page.*

class PageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
