package com.iamsdt.shokherschool.data.retrofit.pojo.comment

import com.google.gson.annotations.SerializedName

data class CommentResponse(@SerializedName("author_name")
                           val authorName: String = "",
                           @SerializedName("date")
                           val date: String = "",
                           @SerializedName("content")
                           val content: Content,
                           @SerializedName("author_avatar_urls")
                           val authorAvatarUrls: AuthorAvatarUrls)