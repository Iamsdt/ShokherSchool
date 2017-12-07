package com.iamsdt.shokherschool.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_main.view.*

/**
* Created by Shudipto Trafder on 11/14/2017.
* at 12:12 AM
*/
class MainAdapter(val clickListener: ClickListener,val context:Context) :
        RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var list: List<PostResponse>? = null

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val post = list!![position]
        holder?.bindTo(post)
    }

    fun replaceList(post: List<PostResponse>) {
        this.list = post
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_row_main, parent, false)

        return MyViewHolder(view)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {

        private val cardView = view.main_card_view
        private val title = view.main_post_title
        private val author = view.main_post_author
        private val date = view.main_post_date

        private val image = view.main_post_img


        var postResponse:PostResponse = PostResponse()

        init {
            cardView.setOnClickListener(this)
        }

        fun bindTo(post: PostResponse) {
            title.text = post.title?.rendered
            //fixme 12/3/2017 replace author id with name
            author.text = post.author.toString()
            //fixme 12/3/2017 format date like 07 may, 2017
            date.text = post.date

            //fixme 12/8/2017 change link of the media
            val link = "link"

            Picasso.with(context).load(link).fit().into(image)

            postResponse = post
        }

        override fun onClick(v: View?) {
            clickListener.onPostItemClick(postResponse)
        }
    }

}
