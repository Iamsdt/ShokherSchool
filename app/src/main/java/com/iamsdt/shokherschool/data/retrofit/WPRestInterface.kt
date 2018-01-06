package com.iamsdt.shokherschool.data.retrofit

import com.iamsdt.shokherschool.data.retrofit.pojo.author.AuthorResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.media.MediaResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.post.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
* Created by Shudipto Trafder on 11/18/2017.
* at 11:27 PM
*/

interface WPRestInterface {

    //get post data
    @GET("posts/")
    fun getAllPostList(): Call<List<PostResponse>>

    @GET("posts/")
    fun getFilterPostList(@Query("before") filter: String): Call<List<PostResponse>>

    @GET("users/{id}")
    fun getAuthorByID(@Path("id") authorId: Int): Call<AuthorResponse>

    /**
     * Returns a single Media item.
     *
     * @param mediaId Id of the Media item
     * @return The Media object
     */
    @GET("media/{id}")
    fun getMediaByID(@Path("id") mediaId: Int): Call<MediaResponse>

}