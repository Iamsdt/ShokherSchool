package com.iamsdt.shokherschool.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.design.widget.Snackbar
import android.support.v4.widget.NestedScrollView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.dao.TagTableDao
import com.iamsdt.shokherschool.data.model.DetailsPostModel
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.data.utilities.Utility
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.viewModel.DetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import timber.log.Timber
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var picasso: Picasso
    @Inject lateinit var categoriesTableDao: CategoriesTableDao
    @Inject lateinit var tagTableDao: TagTableDao

    //view model
    private val viewModel by lazy {
        ViewModelProviders.of(this)
                .get(DetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        //debug only 1/11/2018 remove later
        details_mockLayout.visibility = View.VISIBLE

        //getting intent data
        val postID = intent.getIntExtra(ConstantUtil.intentDetails, 0)

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

        viewModel.getData(postID, postTableDao)?.observe(this,
                Observer<DetailsPostModel> { allData ->
                    if (allData != null) {

                        fillData(allData)

                        //if internet is not present show a message
                        if (!Utility.isNetworkAvailable(this@DetailsActivity)) {
                            Snackbar.make(detailsLayout, "No Internet available\nShowing a catch version", Snackbar.LENGTH_LONG)
                                    .show()
                        }

                        if (!allData.mediaLink.isNullOrEmpty()) {
                            picasso.load(allData.mediaLink).fit()
                                    .centerInside().into(details_img, object : Callback {
                                override fun onSuccess() {
                                    //nothing to do
                                    Timber.i(picasso.snapshot.downloadCount.toString())
                                }

                                override fun onError() {
                                    details_img.visibility = View.GONE
                                }

                            })
                            Timber.i("picasso id: $picasso")
                            Timber.i(picasso.snapshot.toString())
                        } else {
                            details_img.visibility = View.GONE
                        }

                        val content = allData.content
                        webView.loadData(content, "text/html", "UTF-8")
                        details_mockLayout.visibility = View.GONE

                        if (!allData.authorImg.isNullOrEmpty()) {
                            picasso.load(allData.authorImg).fit().into(d_authorImg,
                                    object : Callback {
                                override fun onSuccess() {
                                    //nothing to do
                                }

                                override fun onError() {
                                    d_authorImg.visibility = View.GONE
                                }

                            })
                            Timber.i("picasso id: $picasso")
                        }

                        //set all the text
                        details_title.text = allData.title
                        d_authorName.text = allData.authorName
                        d_authorDetails.text = allData.authorDetails

                        val tag = "Categories: ${allData.categories}  Tags: ${allData.tags}"
                        d_tags.text = tag

                    }
                })

        //detailsScrollView.isSmoothScrollingEnabled = true
        detailsScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                fab.hide()
            } else if (scrollY < oldScrollY) {
                fab.show()
            }
        })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
        //back to home
            android.R.id.home -> onBackPressed()

            R.id.action_settings -> startActivity(Intent(this@DetailsActivity,
                    SettingsActivity::class.java))

            R.id.action_share -> {
                //share logic
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun fillData(details: DetailsPostModel) {
        val thread = HandlerThread("DetailsData")
        thread.start()

        //create a background thread
        val handler = Handler(thread.looper)
        handler.post({
            val tagsList = details.tags?.split(",") ?: arrayListOf()
            val categoriesList = details.categories?.split(",") ?: arrayListOf()

            var tags = ""
            tagsList.filterNot { it.isEmpty() }
                    .map { it.trim().toInt() }
                    .forEach { tags += tagTableDao.getTagName(it) + ", " }

            var categories = ""
            categoriesList.filterNot { it.isEmpty() }
                    .map { it.trim().toInt() }
                    .forEach { categories += categoriesTableDao.getCategoriesName(it) + ", " }

            thread.quitSafely()
            Handler(mainLooper).post({
                val tag = "Categories: $categories  Tags: $tags"
                d_tags.text = tag
            })
        })
    }

}
