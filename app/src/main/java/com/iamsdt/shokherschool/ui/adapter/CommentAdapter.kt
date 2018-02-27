package com.iamsdt.shokherschool.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.retrofit.pojo.comment.CommentResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comment_item.view.*

/**
 * Created by Shudipto Trafder on 2/27/2018.
 * at 5:02 PM
 */

class CommentAdapter(private val commentList: List<CommentResponse>,
                     val picasso: Picasso) :
        RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.color_list_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder?, position: Int) {
        val commentResponse = commentList[position]
        holder?.bindTo(commentResponse)
    }

    inner class CommentViewHolder(viewItem: View)
        : RecyclerView.ViewHolder(viewItem) {

        private val name: TextView = viewItem.comment_name
        private val comment: TextView = viewItem.comment_comment
        private val pic: ImageView = viewItem.comment_pic

        fun bindTo(commentResponse: CommentResponse) {
            name.text = commentResponse.authorName
            comment.text = commentResponse.content.rendered
            picasso.load(commentResponse.authorAvatarUrls.authorAvatarUrls48).fit().into(pic)
        }

    }
}