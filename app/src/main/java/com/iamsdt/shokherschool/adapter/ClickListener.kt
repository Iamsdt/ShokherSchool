package com.iamsdt.shokherschool.adapter

import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse

/**
 * Created by Shudipto Trafder on 11/24/2017.
 * at 4:42 PM
 */
interface ClickListener {
    fun onPostItemClick(post: PostResponse)
}