package com.iamsdt.shokherschool.paged

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.view.ViewGroup
import com.iamsdt.shokherschool.adapter.MainAdapter
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 12:20 AM
 */
class KitsuPagedListAdapter : PagedListAdapter<PostResponse, MainAdapter.MyViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: MainAdapter.MyViewHolder, position: Int) {
        //holder.bindTo(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MyViewHolder = MainAdapter.MyViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffCallback<PostResponse>() {
            override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean = oldItem == newItem
        }
    }
}