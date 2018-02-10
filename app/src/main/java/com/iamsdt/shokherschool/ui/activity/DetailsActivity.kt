package com.iamsdt.shokherschool.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
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

    //bookmark status
    private var bookmarkStatus:Int = 0

    //post id
    private var postID:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        //getting intent data
        postID = intent.getIntExtra(ConstantUtil.intentDetails, 0)

        //initialize web view
        //debug only 11/27/2017 remove later
        val webView = d_webview
        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView,
                                                  request: WebResourceRequest): Boolean {

                //open external link in google chrome
                Utility.customTab(this@DetailsActivity, request.url.toString())
                return true
            }

            //open external link in google chrome
            //using deprecated method
            // some time above method is not call
            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Utility.customTab(this@DetailsActivity, url!!)
                return true
            }
        }

        //web view settings
        val settings = webView!!.settings
        //app web view catching
        //todo 2/10/2018 add settings
        //by default it false
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        settings.allowContentAccess = false
        settings.loadWithOverviewMode = true

        viewModel.getData(postID, postTableDao)?.observe(this,
                Observer<DetailsPostModel> { allData ->
                    if (allData != null) {
                        //update categories and tags
                        fillData(allData)

                        //save bookmark status on global variable
                        bookmarkStatus = allData.bookmark

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
                                    Timber.i(picasso.snapshot.dump().toString())
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

                        webView.loadData(allData.content, "text/html", "UTF-8")

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
                        Timber.i(tag)
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

        //set bookmark menu icon
        val bookmarkItem =menu?.findItem(R.id.action_bookmark)

        if (bookmarkStatus == 0){
            bookmarkItem?.setIcon(R.drawable.ic_bookmark)
            Timber.i("Bookmark status on Menu Create: $bookmarkStatus")

        } else{
            bookmarkItem?.setIcon(R.drawable.ic_bookmark_done)
            Timber.i("Bookmark status on Menu Create: $bookmarkStatus")
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
        //back to home
            android.R.id.home -> onBackPressed()

            R.id.action_bookmark ->
                setOrRemoveBookmark(item)

            R.id.action_settings -> startActivity(Intent(this@DetailsActivity,
                    SettingsActivity::class.java))

            R.id.action_share -> {
                //share logic
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setOrRemoveBookmark(item: MenuItem) {

        if (postID == 0){
            return
        }

        var set = 0
        var delete = 0

        val thread = HandlerThread("BookmarkDetailsClick")
        thread.start()

        Handler(thread.looper).post({
            if (bookmarkStatus == 0){
                set = postTableDao.setBookmark(postID)
            } else {
                delete = postTableDao.deleteBookmark(postID)
            }

            Handler(Looper.getMainLooper()).post({
                if (set > 0){
                    Snackbar.make(detailsLayout, "Added to Bookmark", Snackbar.LENGTH_LONG)
                            .show()
                    item.setIcon(R.drawable.ic_bookmark_done)
                }

                if (delete > 0){
                    Snackbar.make(detailsLayout, "Remove from Bookmark", Snackbar.LENGTH_LONG)
                            .show()
                    item.setIcon(R.drawable.ic_bookmark)
                }
            })

            //release thread
            thread.quitSafely()
        })
    }


    /**
     *This method is for update tag and categories to name
     * in background
     *
     * @param details Post Model Class Details*/

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

            //release thread
            thread.quitSafely()

            //create new thread with main thread
            Handler(mainLooper).post({
                val tag = "Categories: $categories  Tags: $tags"
                Timber.i("New tag $tag")
                d_tags.text = tag
            })
        })
    }

}
