package com.iamsdt.shokherschool.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.database.dao.PageTableDao
import com.iamsdt.shokherschool.data.model.PostModel
import com.iamsdt.shokherschool.data.utilities.MyDateUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_main.view.*
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 2/10/2018.
 * at 9:55 PM
 */
class PageAdapter(val picasso: Picasso, val activity: Activity,
                  val pageTableDao: PageTableDao) :
        RecyclerView.Adapter<PageAdapter.MyViewHolder>() {

    private var context:Context ?= null

    private var list: List<PostModel>? = null

    init {
        context = activity.applicationContext
    }

    //change context according to activity
    fun changeContext(newContext: Context){
        context = newContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_main, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = list!![position]
        holder.bindTo(post)
    }

    fun replaceList(post: List<PostModel>) {
        this.list = post
        notifyDataSetChanged()
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.main_card_view
        val bookmarkImg: ImageView = view.main_bookmark
        private val title = view.main_post_title
        private val author = view.main_post_author
        private val date = view.main_post_date

        private val image = view.main_post_img

        fun bindTo(post: PostModel) {
            title.text = post.title
            author.text = post.author
            val readableDate = MyDateUtil.getReadableDate(post.date)
            date.text = readableDate
            val link = post.mediaLink

            if (!link.isNullOrEmpty()) {
                picasso.load(link).fit().into(image,object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError() {
                        image.visibility = View.GONE
                    }

                })
                Timber.i("picasso id: $picasso")
            }
        }
    }
}