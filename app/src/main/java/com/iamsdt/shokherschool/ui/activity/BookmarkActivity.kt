package com.iamsdt.shokherschool.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.ui.adapter.MainAdapter
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.viewModel.BookmarkViewModel
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.content_bookmark.*
import javax.inject.Inject

class BookmarkActivity : BaseActivity() {

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var mAdapter: MainAdapter

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        setSupportActionBar(toolbar)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        bookmarkRcv.layoutManager = manager
        bookmarkRcv.adapter = mAdapter
        bookmarkRcv.itemAnimator = DefaultItemAnimator()

        mAdapter.changeContext(this@BookmarkActivity)

        viewModel.getData(postTableDao)?.observe(this, Observer { data ->
            if (data != null && data.isNotEmpty()) {
                bookmarkProgressBar.visibility = View.GONE
                mAdapter.replaceList(data)
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
