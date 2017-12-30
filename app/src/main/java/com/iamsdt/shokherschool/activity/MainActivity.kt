package com.iamsdt.shokherschool.activity

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
import com.iamsdt.shokherschool.BaseActivity
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.adapter.ClickListener
import com.iamsdt.shokherschool.adapter.MainAdapter
import com.iamsdt.shokherschool.utilities.ConstantUtil
import com.iamsdt.shokherschool.utilities.DataInsert
import com.iamsdt.shokherschool.utilities.MyDateUtil
import com.iamsdt.shokherschool.viewModel.MainPostModelClass
import com.iamsdt.shokherschool.viewModel.MainVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(),
        NavigationView.OnNavigationItemSelectedListener,ClickListener{

    //main adapter for recyclerView
    @Inject lateinit var adapter:MainAdapter

    //request to prevent duplicate request for getting new data
    //on view model class
    private var request:Boolean = false

    //view model
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //dagger inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)

        mainRcv.layoutManager = manager
        mainRcv.adapter = adapter
        mainRcv.itemAnimator = DefaultItemAnimator()

        viewModel.getAllPostList()?.observe(this,
                Observer<List<MainPostModelClass>> { allPost ->
                    if (allPost != null && !allPost.isEmpty()){
                        adapter.replaceList(allPost)
                        mainProgressBar.visibility = View.GONE
                        Timber.i("Item Size of adapter:${allPost.size}")
                        request = false
                        saveDate()
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
                    //Toast.makeText(baseContext,"last",Toast.LENGTH_SHORT).show()
                    if (!request){
                        viewModel.requestNewPost(MyDateUtil.getDate(this@MainActivity))
                        request = true
                        Timber.i("New request start")
                    }
                }

            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        DataInsert.dataInsertStart(this)
    }

    private fun saveDate(){
        Thread(Runnable {
            viewModel.saveDate(baseContext)
            Timber.i("thread finished")
        }).start()
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
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {
                startActivity(Intent(baseContext, DetailsActivity::class.java))
            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostItemClick(post: MainPostModelClass) {
        val intent = Intent(baseContext, DetailsActivity::class.java)
        intent.putExtra(ConstantUtil.intentPostID,post.id)
        intent.putExtra(ConstantUtil.intentPostDate,post.date)
        intent.putExtra(ConstantUtil.intentPostAuthor,post.author)
        intent.putExtra(ConstantUtil.intentPostTitle,post.title)
        startActivity(intent)
    }
}