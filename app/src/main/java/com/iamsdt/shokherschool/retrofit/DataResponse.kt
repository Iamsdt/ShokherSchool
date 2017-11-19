package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.post.Post
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Shudipto Trafder on 11/18/2017.
 */

interface DataResponse{

    @GET("posts/?per_page=10")
    fun getAllPostResponse(): Call<List<Post>>

    companion object {
        val retrofitBaseUrl:String = "https://shokherschool.com/wp-json/wp/v2/"
    }
}