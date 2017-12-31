package com.iamsdt.shokherschool.retrofit.pojo

import com.google.gson.annotations.SerializedName

data class Post(val date: String = "",
                @SerializedName("_links")
                val author: Int = 0,
                val link: String = "",
                val title: Title? = null,
                @SerializedName("comment_status")
                val commentStatus: String = "",
                @SerializedName("featured_media")
                val featuredMedia: Int = 0,
                val sticky: Boolean = false,
                val id: Int = 0,
                val categories: List<Int>? = null)