package com.iamsdt.shokherschool.ui.adapter

import com.iamsdt.shokherschool.data.model.PostModel

/**
 * Created by Shudipto Trafder on 11/24/2017.
 * at 4:42 PM
 */
interface ClickListener {
    fun onPostItemClick(post: PostModel)
}