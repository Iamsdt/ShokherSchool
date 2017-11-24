package com.iamsdt.shokherschool.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import kotlinx.android.synthetic.main.item_row.view.*

/**
 * Created by Shudipto Trafder on 11/14/2017.
 */
class MainAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var list: List<PostResponse>? = null

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val post = list!![position]
        holder!!.bindTo(post)
    }

    fun replaceList(post: List<PostResponse>) {
        this.list = post
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_row, parent, false)

        return MyViewHolder(view)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {

        val id: TextView = view.cat_id

        val name: TextView = view.name
        val link: TextView = view.link
        val number: TextView = view.number

        var linkOfPost:String = ""

        init {
            link.setOnClickListener(this)
        }

        fun bindTo(post: PostResponse) {
            id.text = post.id.toString()
            link.text = post.link
            number.text = post.date
            name.text = post.title!!.rendered
            linkOfPost = post.link
        }

        override fun onClick(v: View?) {
            clickListener.onPostItemClick(linkOfPost)
        }
    }

}
