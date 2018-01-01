package com.iamsdt.shokherschool.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.iamsdt.shokherschool.BaseActivity
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.iamsdt.shokherschool.viewModel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    //dao to access database
    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var mediaTableDao: MediaTableDao
    @Inject lateinit var authorTableDao: AuthorTableDao

    //rest interface to get data from server
    @Inject lateinit var wpRestInterface: WPRestInterface


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setSupportActionBar(toolbar)

        viewModel.getAllPostList(postTableDao,
                mediaTableDao, authorTableDao, wpRestInterface)?.observe(this, Observer { allData ->
            if (allData != null && allData.isNotEmpty()) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
