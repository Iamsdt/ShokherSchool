package com.iamsdt.shokherschool

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.shokherschool.utilities.Utility
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import org.jsoup.Jsoup
import java.io.IOException

class DetailsActivity : AppCompatActivity() {

    var uiHandler = Handler()
    var postLink: String? = null
    private var webView:WebView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        postLink = intent.getStringExtra(Intent.EXTRA_TEXT)
        webView = d_webview
        webView?.webViewClient = object : WebViewClient(){
//            override fun shouldOverrideUrlLoading(view: WebView,
//                                                  request: WebResourceRequest): Boolean {
//                val intent = Intent(Intent.ACTION_VIEW, request.url)
//                startActivity(Intent.createChooser(intent,"Chose browser"))
//                return super.shouldOverrideUrlLoading(view, request)
//            }

            //using deprecated method
            //don't find possible solution
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Utility.customTab(this@DetailsActivity,url!!)
                return super.shouldOverrideUrlLoading(view, url)
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

    inner class BackgroundWorker:AsyncTask<Void,Void,Void>() {

        override fun doInBackground(vararg params: Void?): Void? {

            try {
                val htmlDocument = Jsoup.connect(postLink).get()

                val element = htmlDocument.getElementsByClass("entry-content")
                // replace body with selected element
                htmlDocument.body().empty().append(element.toString())
                val html = htmlDocument.toString()

                //uiHandler.post({ details_webview.text = element.text() })
                uiHandler.post({
                    webView!!.loadData(html, "text/html", "UTF-8")
                })
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }
    }
}
