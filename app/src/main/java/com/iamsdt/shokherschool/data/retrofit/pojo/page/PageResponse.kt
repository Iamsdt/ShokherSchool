package com.iamsdt.shokherschool.data.retrofit.pojo.page

import com.google.gson.annotations.SerializedName

data class PageResponse(val date: String = "",
                        val author: Int = 0,
                        val title: Title?,
                        val content: Content?,
                        @SerializedName("featured_media")
                        val featuredMedia: Int = 0,
                        val id: Int = 0)