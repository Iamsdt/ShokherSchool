package com.iamsdt.shokherschool.data.retrofit.pojo.comment

import com.google.gson.annotations.SerializedName

data class AuthorAvatarUrls(@SerializedName("24")
                            val authorAvatarUrls24: String = "",
                            @SerializedName("48")
                            val authorAvatarUrls48: String = "",
                            @SerializedName("96")
                            val authorAvatarUrls96: String = "")