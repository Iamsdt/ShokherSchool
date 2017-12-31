package com.iamsdt.shokherschool.retrofit.pojo.post.page

import com.google.gson.annotations.SerializedName

data class Author(@SerializedName("avatar_urls")
                  val avatarUrls: AvatarUrls? = null,
                  val name: String = "",
                  val link: String = "",
                  val description: String = "",
                  val id: Int = 0,
                  val url: String = "",
                  val slug: String = "")