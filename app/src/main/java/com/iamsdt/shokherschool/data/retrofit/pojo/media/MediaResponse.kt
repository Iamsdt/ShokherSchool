package com.iamsdt.shokherschool.data.retrofit.pojo.media

import com.google.gson.annotations.SerializedName

data class MediaResponse(val id: Int = 0,
                 val title: Title?,
                 @SerializedName("media_details")
                 val mediaDetails: MediaDetails?)