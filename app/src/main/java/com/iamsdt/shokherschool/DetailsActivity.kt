package com.iamsdt.shokherschool

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.shokherschool.utilities.ConstantUtil
import com.iamsdt.shokherschool.utilities.Utility
import com.iamsdt.shokherschool.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.post_head.*

class DetailsActivity : AppCompatActivity() {

    private var postID: Int? = null
    private var postDate: String? = null
    private var postTitle: String? = null
    private var postAuthor: String? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        //set comment option disable
        d_comment_form.visibility = View.GONE


        //getting intent data
        postID = intent.getIntExtra(ConstantUtil.intentPostID, 0)
        postDate = intent.getStringExtra(ConstantUtil.intentPostDate)
        postAuthor = intent.getStringExtra(ConstantUtil.intentPostAuthor)
        postTitle = intent.getStringExtra(ConstantUtil.intentPostTitle)

        viewModel.id = postID!!

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
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Utility.customTab(this@DetailsActivity, url!!)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                details_mockLayout.visibility = View.GONE
            }

        }

        val settings = webView!!.settings
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.allowContentAccess = false
        settings.loadWithOverviewMode = true

        //set all the text
        d_title.text = postTitle
        d_date.text = postDate
        d_author.text = postAuthor

        //initialize viewModel
        viewModel.getHtmlData()?.observe(this, Observer<String> { htmlData ->
            if (htmlData != null && !htmlData.isEmpty()) {
                webView.loadData(htmlData,"text/html","UTF-8")
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
