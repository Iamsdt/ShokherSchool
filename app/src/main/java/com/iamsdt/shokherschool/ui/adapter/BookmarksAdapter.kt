package com.iamsdt.shokherschool.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.design.widget.Snackbar
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.model.PostModel
import com.iamsdt.shokherschool.data.utilities.ConstantUtil
import com.iamsdt.shokherschool.ui.activity.DetailsActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_main.view.*
import kotlinx.android.synthetic.main.undo_layout.view.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Shudipto Trafder on 1/14/2018.
 * at 8:39 PM
 */
class BookmarksAdapter(val picasso: Picasso, val activity: Activity,
                       val postTableDao: PostTableDao):
        RecyclerView.Adapter<BookmarksAdapter.MyViewHolder>() {

    // handler for running delayed runnable
    private val handler = Handler()

    // map of items to pending runnable, so we can cancel a removal if need be
    private val pendingRunnable:HashMap<Int,Runnable> = hashMapOf()


    private var list: ArrayList<PostModel>? = null
    private val itemsPendingRemoval: ArrayList<Int> = ArrayList()

    private var mContext: Context? = null

    init {
        mContext = activity.applicationContext
    }

    //change context according to activity
    fun changeContext(newContext: Context){
        mContext = newContext
    }

    override fun onBindViewHolder(holder: BookmarksAdapter.MyViewHolder, position: Int) {
        val post = list!![position]

        if (itemsPendingRemoval.contains(post.id)) {
            holder.cardView.visibility = View.GONE
            holder.undoLayout.visibility = View.VISIBLE

            //undo click listener
            holder.undo.setOnClickListener({
                undoOpt(post)
            })
        } else{
            //show regular layout
            holder.cardView.visibility = View.VISIBLE
            holder.undoLayout.visibility = View.GONE

            holder.bindTo(post)

            holder.cardView.setOnClickListener {
                mContext?.startActivity(
                        Intent(mContext, DetailsActivity::class.java)
                                .putExtra(ConstantUtil.intentDetails,post.id))
            }

            val book = post.bookmark
            if (book == 1){
                //change image bcg
                holder.bookmarkImg.setImageDrawable(mContext?.getDrawable(R.drawable.ic_bookmark_done))
            }
        }
    }

    private fun undoOpt(post:PostModel) {
        val pendingRemovalRunnable = pendingRunnable[post.id]
        pendingRunnable.remove(post.id)
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable)
        itemsPendingRemoval.remove(post.id)
        // this will rebind the row in "normal" state

        val position = list!!.indexOf(post)

        notifyItemChanged(position)
    }

    fun pendingRemoval(position: Int, view: View) {

        val postId = list?.get(position)?.id ?: 0

        if (!itemsPendingRemoval.contains(postId)) {
            itemsPendingRemoval.add(postId)
            // this will redraw row in "undo" state
            notifyItemChanged(position)
            // let's create, store and post a runnable to remove the postId
            val pendingRemovalRunnable = Runnable {
                remove(position,view)
            }

            handler.postDelayed(pendingRemovalRunnable, 3000)

            pendingRunnable.put(postId, pendingRemovalRunnable)
        }
    }

    private fun remove(position: Int, view: View) {
        val postID = list?.get(position)?.id ?: 0
        if (itemsPendingRemoval.contains(postID)) {
            itemsPendingRemoval.remove(postID)
        }
        if (list!!.contains(list!![position])) {
            list?.remove(list!![position])
            var update: Int

            val thread = HandlerThread("Bookmark")
            thread.start()
            Handler(thread.looper).post({

                update = postTableDao.deleteBookmark(postID)

                if (update != -1){
                    Handler(Looper.getMainLooper()).post({
                        notifyItemRemoved(position)
                        Snackbar.make(view,"One Item removed",Snackbar.LENGTH_SHORT).show()
                    })
                }

                thread.quitSafely()
            })

        }
    }

    fun isPendingRemoval(position: Int): Boolean {
        val data = list?.get(position)?.id ?: 0
        return itemsPendingRemoval.contains(data)
    }

    fun replaceList(post: List<PostModel>) {
        val li = ArrayList<PostModel>(post)
        this.list = li
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bookmark_item, parent, false)

        return MyViewHolder(view)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val cardView: CardView = view.main_card_view
        val bookmarkImg: ImageView = view.main_bookmark
        private val title = view.main_post_title
        private val author = view.main_post_author
        private val date = view.main_post_date

        private val image = view.main_post_img

        val undo:TextView = view.undoBtn
        val undoLayout:LinearLayout = view.swipeLayout

        fun bindTo(post: PostModel) {
            title.text = post.title
            author.text = post.author
            date.text = post.date
            val link = post.mediaLink

            if (!link.isNullOrEmpty()) {
                picasso.load(link).fit().into(image, object : Callback {
                    override fun onSuccess() {
                        //nothing to do
                    }

                    override fun onError() {
                        image.visibility = View.GONE
                    }

                })
            }
        }
    }
}