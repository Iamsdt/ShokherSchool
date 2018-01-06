package com.iamsdt.shokherschool.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.shokherschool.BaseActivity
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.model.DetailsPostModel
import com.iamsdt.shokherschool.utilities.ConstantUtil
import com.iamsdt.shokherschool.utilities.Utility
import com.iamsdt.shokherschool.viewModel.DetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.post_head.*
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject lateinit var postTableDao:PostTableDao
    @Inject lateinit var picasso:Picasso

    //view model
    private val viewModel by lazy {
        ViewModelProviders.of(this)
                .get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        //set comment option disable
        //d_comment_form.visibility = View.GONE

        //getting intent data
        val postID = intent.getIntExtra(ConstantUtil.intentDetails,0)

        //initialize web view
        //debug only 11/27/2017 remove later
        val webView = d_webview
        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView,
                                                  request: WebResourceRequest): Boolean {
                Utility.customTab(this@DetailsActivity, request.url.toString())
                return true
            }

            //using deprecated method
            //don't find possible solution
            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Utility.customTab(this@DetailsActivity, url!!)
                return true
            }
        }

        val settings = webView!!.settings
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.allowContentAccess = false
        settings.loadWithOverviewMode = false

        viewModel.getData(postID,postTableDao)?.observe(this, Observer<DetailsPostModel> { allData ->
            if (allData != null) {
                webView.loadData(allData.content, "text/html", "UTF-8")
                details_mockLayout.visibility = View.GONE
                //set all the text
                d_title.text = allData.title ?: "no title found"
                d_date.text = allData.date ?: "no post date found"
                d_author.text = allData.authorName ?: "no author found"
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //buy calling android.R.id.home

        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}
