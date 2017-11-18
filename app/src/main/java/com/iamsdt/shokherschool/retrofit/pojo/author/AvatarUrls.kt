package com.iamsdt.shokherschool.retrofit.pojo.author

import com.google.gson.annotations.SerializedName

data class AvatarUrls(@SerializedName("24")
                      val avatar24: String = "",
                      @SerializedName("48")
                      val avatar48: String = "",
                      @SerializedName("96")
                      val avatar96: String = "")