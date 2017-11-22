package com.iamsdt.shokherschool.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.retrofit.pojo.post.Post
import kotlinx.android.synthetic.main.item_row.view.*

/**
* Created by Shudipto Trafder on 11/14/2017.
*/
class MainAdapter:RecyclerView.Adapter<MainAdapter.MyViewHolder>(){

    private var list:List<Post> ?= null

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val post:Post = list!![position]

        holder!!.id.text = post.id.toString()
        holder.link.text = post.link
        holder.number.text = post.date
        holder.name.text = post.title!!.rendered
    }

    fun replaceList(list: List<Post>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_row,parent,false)

        return MyViewHolder(view)
    }

    inner class MyViewHolder(view:View): RecyclerView.ViewHolder(view){

        val id:TextView = view.cat_id
        val name: TextView = view.name
        val link:TextView = view.link
        val number: TextView = view.number

    }

}