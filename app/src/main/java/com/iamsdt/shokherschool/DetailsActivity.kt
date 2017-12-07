package com.iamsdt.shokherschool

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
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
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.post_head.*
import org.jsoup.Jsoup
import java.io.IOException

class DetailsActivity : AppCompatActivity() {

    private var uiHandler = Handler()
    private var postLink: String? = null
    private var postDate: String? = null
    private var postTitle: String? = null
    private var postAuthor: String? = null

    private var webView:WebView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        //set comment opption disable
        d_comment_form.visibility = View.GONE


        //getting intent data
        postLink = intent.getStringExtra(ConstantUtil.intentPostLink)
        postDate = intent.getStringExtra(ConstantUtil.intentPostDate)
        postAuthor = intent.getStringExtra(ConstantUtil.intentPostAuthorID)
        postTitle = intent.getStringExtra(ConstantUtil.intentPostTitle)


        //initialize webview
        //debug only 11/27/2017 remove later
        webView = d_webview
        webView?.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView,
                                                  request: WebResourceRequest): Boolean {
                Utility.customTab(this@DetailsActivity,request.url.toString())
                return true
        }

            //using deprecated method
            //don't find possible solution
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Utility.customTab(this@DetailsActivity,url!!)
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

        val backgroundWorker = BackgroundWorker()
        backgroundWorker.execute()

        //set all the text
        d_title.text = postTitle
        d_date.text = postDate
        d_author.text = postAuthor

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

    @SuppressLint("StaticFieldLeak")
    inner class BackgroundWorker:AsyncTask<Void,Void,Void>() {

        override fun doInBackground(vararg params: Void?): Void? {

            try {
                val htmlDocument = Jsoup.connect(postLink).get()

                val element = htmlDocument
                        .getElementsByClass("entry-content")

                // replace with selected element
                //because we need theme of
                htmlDocument.empty().append(element.toString())

                //now remove social content
                htmlDocument.getElementsByClass("apss-social-share")
                        .first().remove()

                uiHandler.post({
                    //fixme 12/8/2017 move this data to viewModel
                    webView!!.loadData(htmlDocument.toString(),
                            "text/html", "UTF-8")
                    details_mockLayout.visibility = View.GONE
                })
            } catch (e: IOException) {
                e.printStackTrace()
                Utility.logger("Jsoup data failed","Jsoup",e)
            }

            return null
        }
    }
}
