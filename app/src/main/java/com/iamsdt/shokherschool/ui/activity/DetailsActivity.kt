package com.iamsdt.shokherschool.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.*
import android.support.design.widget.Snackbar
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.dao.TagTableDao
import com.iamsdt.shokherschool.data.model.DetailsPostModel
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.retrofit.pojo.comment.CommentResponse
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.data.utilities.Utility
import com.iamsdt.shokherschool.ui.adapter.CommentAdapter
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.viewModel.DetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.comment_form.*
import kotlinx.android.synthetic.main.content_details.*
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class DetailsActivity : BaseActivity() {

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var picasso: Picasso
    @Inject lateinit var categoriesTableDao: CategoriesTableDao
    @Inject lateinit var tagTableDao: TagTableDao

    @Inject @Named("detailsRest") lateinit var wpRestInterface: WPRestInterface

    //view model
    private val viewModel by lazy {
        ViewModelProviders.of(this)
                .get(DetailsViewModel::class.java)
    }

    //bookmark status
    private var bookmarkStatus:Int = 0

    //post id
    private var postID:Int = 0

    //share text
    private var shareText = ""
    private var sap:ShareActionProvider ?= null


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
        //complete only 11/27/2017 remove later
        //val webView = d_webview
        d_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView,
                                                  request: WebResourceRequest): Boolean {

                //open external link in google chrome
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Utility.customTab(this@DetailsActivity, request.url.toString())
                } else {
                    //Utility.customTab(this@DetailsActivity, request.url.toString())
                }
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
        val settings = d_webview!!.settings
        //app web view catching
        //todo 2/10/2018 add settings
        //by default it false
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        settings.allowContentAccess = false
        settings.loadWithOverviewMode = true

        //debug only 2/23/2018 remove later
        Timber.i("*****WP interface from details Activity $wpRestInterface")

        viewModel.getData(postID, postTableDao)?.observe(this,
                Observer<DetailsPostModel> { allData ->
                    if (allData != null) {
                        //update categories and tags
                        fillData(allData)

                        //save title for share intent
                        shareText = allData.title ?:"no title"
                        //update share action provider
                        //resetSap()

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

                        d_webview.loadData(allData.content, "text/html", "UTF-8")

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
            //todo 2/10/2018 add comment option
            //postComment(view)
        }

        //get comments
        getComments()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun postComment(view: View?) {
        val builder = AlertDialog.Builder(this@DetailsActivity)
        val dialogView = layoutInflater.inflate(R.layout.comment_form,null)
        builder.setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()
        //set touch outside off
        alertDialog.setCanceledOnTouchOutside(false)

        c_button.setOnClickListener({
            val name = c_nameET.editableText.toString()
            val email = c_commentEt.editableText.toString()
            val comment = c_commentEt.editableText.toString()

            if (name.isNotEmpty() && name.length >= 3){

                if (email.isNotEmpty() && email.length >= 5 && email.contains("@")){

                    if (comment.isNotEmpty() && comment.length >= 5){
                        //all ok do something
                        //val data = wpRestInterface.createComment()
                        Toast.makeText(this,"Not available now",Toast.LENGTH_SHORT).show()

                    } else{
                        c_comment_lay.error = "Too small comment!"
                    }
                } else{
                    c_email_lay.error = "Please input correct email"
                }
            } else{
                c_name_lay.error = "Please input your name"
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)

        //set bookmark menu icon
        val bookmarkItem =menu?.findItem(R.id.action_bookmark)
        //for bookmark
        if (bookmarkStatus == 0){
            bookmarkItem?.setIcon(R.drawable.ic_bookmark)
            Timber.i("Bookmark status on Menu Create: $bookmarkStatus")

        } else{
            bookmarkItem?.setIcon(R.drawable.ic_bookmark_done)
            Timber.i("Bookmark status on Menu Create: $bookmarkStatus")
        }

        //val shareMenuItem = menu?.findItem(R.id.action_share)
        //sap = MenuItemCompat.getActionProvider(shareMenuItem) as ShareActionProvider
        //sap?.setShareIntent(createShareIntent())

        return true
    }

    /**Create intent for share action provide
     * @return intent
     */
    private fun createShareIntent(): Intent? {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val share = shareText + "... read more on -> https://shokherschool.com/?p=$postID"
        shareIntent.putExtra(Intent.EXTRA_TEXT,share)
        return shareIntent
    }

    //doesn't require, it will not update later
    /** Reset share action provider
     * after update the data
     */
//    private fun resetSap() {
//        if (sap != null) {
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.type = "text/plain"
//            val share = shareText + "read more on: https://shokherschool.com/?p=$postID"
//            shareIntent.putExtra(Intent.EXTRA_TEXT, share)
//
//            sap?.setShareIntent(shareIntent)
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
        //back to home
            android.R.id.home -> onBackPressed()

            R.id.action_bookmark ->
                setOrRemoveBookmark(item)

            R.id.action_settings -> startActivity(Intent(this@DetailsActivity,
                    SettingsActivity::class.java))

//            R.id.action_share -> {
//                //share logic
//            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Method for set or remove bookmark
     * @param item menu item
     */
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

                //release thread
                thread.quitSafely()

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

            //create new thread with main thread
            Handler(mainLooper).post({
                //release thread
                thread.quitSafely()

                val tag = "Categories: $categories  Tags: $tags"
                Timber.i("New tag $tag")
                d_tags.text = tag
            })
        })
    }


    //complete get comments
    private fun getComments(){
        val thread = HandlerThread("BookmarkDetailsClick")
        thread.start()

        val handler = Handler(thread.looper)
        handler.post({
            val callback = wpRestInterface.getCommentForId(postID)
            Timber.i("****** link: ${callback.request().url()} **********")
            callback.enqueue(object :retrofit2.Callback<List<CommentResponse>> {
                override fun onFailure(call: Call<List<CommentResponse>>?, t: Throwable?) {
                    details_comment_form.visibility = View.GONE
                }

                override fun onResponse(call: Call<List<CommentResponse>>?, response: Response<List<CommentResponse>>?) {
                    val list:List<CommentResponse> = response?.body() ?: arrayListOf()

                    val postHandler = Handler(Looper.getMainLooper())
                    postHandler.post({

                        //release thread
                        thread.quitSafely()

                        if (list.isNotEmpty()) {
                            details_comment_form.visibility = View.VISIBLE
                            details_no_comment.visibility = View.GONE

                            //get comments
                            val manager = LinearLayoutManager(this@DetailsActivity,
                                    LinearLayoutManager.VERTICAL, false)

                            details_comment_form.layoutManager = manager
                            details_comment_form.adapter = CommentAdapter(list, picasso)
                        }
                        //release thread
                        thread.quitSafely()
                    })

                }

            })
        })
    }

}
