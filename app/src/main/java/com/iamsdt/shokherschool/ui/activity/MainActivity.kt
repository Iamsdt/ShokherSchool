package com.iamsdt.shokherschool.ui.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.model.PostModel
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.data.utilities.MyDateUtil
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.data.utilities.Utility
import com.iamsdt.shokherschool.ui.adapter.MainAdapter
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.services.DataInsertService
import com.iamsdt.shokherschool.ui.viewModel.MainVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(),
        NavigationView.OnNavigationItemSelectedListener{

    //main adapter for recyclerView
    @Inject lateinit var adapter:MainAdapter

    //dao to access database
    @Inject lateinit var postTableDao:PostTableDao
    @Inject lateinit var authorTableDao:AuthorTableDao

    //rest interface to get data from server
    @Inject lateinit var wpRestInterface:WPRestInterface

    //theme request code
    private val themeRequestCode = 121

    //view model
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //dagger inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)

        mainRcv.layoutManager = manager
        mainRcv.adapter = adapter
        mainRcv.itemAnimator = DefaultItemAnimator()

        adapter.changeContext(this@MainActivity)

        viewModel.setup(postTableDao,authorTableDao,wpRestInterface)
        viewModel.getAllPostList()?.observe(this,
                Observer<List<PostModel>> { allPost ->
                    if (allPost != null && !allPost.isEmpty()){
                        adapter.replaceList(allPost)
                        mainProgressBar.visibility = View.GONE
                        Timber.i("Item Size of adapter:${allPost.size}")
                        saveDate()

                        //if internet is not present show a message
                        if (!Utility.isNetworkAvailable(this@MainActivity)){
                            Snackbar.make(mainLayout, "No Internet available", Snackbar.LENGTH_LONG)
                                    .show()
                        }

                        if (showNewDataToast){
                                Snackbar.make(mainLayout, "New post found", Snackbar.LENGTH_SHORT)
                                        .show()
                            showNewDataToast = false

                        }
                    }
                })

        //scroll listener to lode more data on scroll
        mainRcv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = manager.itemCount
                val lastVisible = manager.findLastVisibleItemPosition()

                val endHasBeenReached = lastVisible + 5 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached) {
                    //completed 1/1/2018 prevent multiple request
                    if (!request && Utility.isNetworkAvailable(this@MainActivity)){
                        viewModel.requestNewPost(wpRestInterface,
                                MyDateUtil.getDate(this@MainActivity))
                        request = true
                        Timber.i("New request start")
                    }
                }

            }
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (DataInsertService.isRunning){
            Timber.w("Service is ruining on Main Activity")
            //if all the task is done then
            //stop the service
            if (DataInsertService.isServiceCompleted()) {
                val intent = Intent(this@MainActivity,DataInsertService::class.java)
                stopService(intent)
            }
        }
    }

    private fun saveDate(){
        //complete 1/15/2018 remove thread
        viewModel.saveDate(baseContext)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->{
                startActivity(Intent(baseContext, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_bookmark -> {
                startActivity(Intent(this@MainActivity,BookmarkActivity::class.java))
            }

            R.id.nav_categories -> {

            }

            R.id.nav_page -> {

            }

            R.id.nav_setting -> {
                startActivity(Intent(this@MainActivity,SettingsActivity::class.java))
            }

            R.id.nav_choseColor -> {
                startActivityForResult(
                        ColorActivity.createIntent(this),
                        themeRequestCode)
            }

            R.id.nav_about -> {
            }

            R.id.nav_copyright -> {
            }

            R.id.nav_tms -> {
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == themeRequestCode && resultCode == Activity.RESULT_OK){
            //recreate this activity
            recreate()
        }
    }

    companion object {
        //request to prevent duplicate request for getting new data
        //on view model class
        var request:Boolean = false
        var showNewDataToast:Boolean = false
    }
}
