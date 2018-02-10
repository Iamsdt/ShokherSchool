package com.iamsdt.shokherschool.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.utilities.SwipeUtil
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.ui.adapter.BookmarksAdapter
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.viewModel.BookmarkViewModel
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.content_bookmark.*
import javax.inject.Inject

class BookmarkActivity : BaseActivity() {

    @Inject lateinit var postTableDao: PostTableDao
    @Inject lateinit var mAdapter: BookmarksAdapter

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_bookmark)
        setSupportActionBar(toolbar)

        val manager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        bookmarkRcv.layoutManager = manager
        bookmarkRcv.adapter = mAdapter
        bookmarkRcv.itemAnimator = DefaultItemAnimator()

        mAdapter.changeContext(this@BookmarkActivity)

        setNoFavouriteMode()

        viewModel.getData(postTableDao)?.observe(this, Observer { data ->
            if (data != null && data.isNotEmpty()) {
                setFavouriteMode()
                mAdapter.replaceList(data)
                setSwipeForRecyclerView()
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setNoFavouriteMode() {
        no_favouriteLayout.visibility = View.VISIBLE
        bookmarkRcv.visibility = View.GONE
    }

    private fun setFavouriteMode() {
        no_favouriteLayout.visibility = View.GONE
        bookmarkRcv.visibility = View.VISIBLE
    }

    private fun setSwipeForRecyclerView() {

        val swipeHelper = object : SwipeUtil(0,
                ItemTouchHelper.START or ItemTouchHelper.END, this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val adapter = bookmarkRcv.adapter as BookmarksAdapter
                adapter.pendingRemoval(swipedPosition, bookmarkCoordinatorLayout)
            }

            override fun getSwipeDirs(recyclerView: RecyclerView,
                                      viewHolder: RecyclerView.ViewHolder): Int {
                val position = viewHolder.adapterPosition
                val adapter = recyclerView.adapter as BookmarksAdapter
                return if (adapter.isPendingRemoval(position)) {
                    0
                } else super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        val mItemTouchHelper = ItemTouchHelper(swipeHelper)
        mItemTouchHelper.attachToRecyclerView(bookmarkRcv)

        //set swipe label
        swipeHelper.leftSwipeLabel = ("Item removed")
        //set swipe background-Color
        swipeHelper.leftColorCode = (ContextCompat.getColor(this, R.color.red_300))

    }
}
