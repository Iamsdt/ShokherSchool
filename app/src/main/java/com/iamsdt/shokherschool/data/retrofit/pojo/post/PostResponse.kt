package com.iamsdt.shokherschool.data.retrofit.pojo.post

import com.google.gson.annotations.SerializedName

data class PostResponse(val date: String = "",
                        val author: Int = 0,
                        val title: Title?,
                        @SerializedName("comment_status")
                        val commentStatus: String = "",
                        val content: Content?,
                        @SerializedName("featured_media")
                        val featuredMedia: Int = 0,
                        val tags: List<Int>?,
                        val id: Int = 0,
                        val categories: List<Int>?)