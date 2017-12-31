package com.iamsdt.shokherschool.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.activity.DetailsActivity
import com.iamsdt.shokherschool.model.PostModel
import com.iamsdt.shokherschool.utilities.ConstantUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_main.view.*

/**
* Created by Shudipto Trafder on 11/14/2017.
* at 12:12 AM
*/
class MainAdapter(val picasso: Picasso,activityContext:Activity) :
        RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    //list
    private var list: List<PostModel>? = null

    //context
    private val context:Context = activityContext.baseContext

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val post = list!![position]
        holder?.bindTo(post)

        holder!!.cardView.setOnClickListener {
            context.startActivity(
                    Intent(context,DetailsActivity::class.java)
                    .putExtra(ConstantUtil.intentPostID,post.id))
        }
    }

    fun replaceList(post: List<PostModel>) {
        this.list = post
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_row_main, parent, false)

        return MyViewHolder(view)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val cardView:CardView = view.main_card_view
        private val title = view.main_post_title
        private val author = view.main_post_author
        private val date = view.main_post_date

        private val image = view.main_post_img

        fun bindTo(post: PostModel) {
            title.text = post.title
            author.text = post.author
            date.text = post.date
            val link = post.mediaLink

            picasso.load(link).fit().into(image)
        }
    }

}
