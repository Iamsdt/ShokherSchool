package com.iamsdt.shokherschool.retrofit.pojo.post

import com.google.gson.annotations.SerializedName

data class PostResponse(val date: String = "",
                val author: Int = 0,
                val id: Int = 0,
                //val categories: List<Int>?,
                val title: Title?,
                @SerializedName("featured_media")
                val featuredMedia: Int = 0
                //val tags: List<Int>?
)