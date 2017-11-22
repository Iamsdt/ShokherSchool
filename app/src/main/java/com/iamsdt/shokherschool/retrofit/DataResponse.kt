package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.post.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
* Created by Shudipto Trafder on 11/18/2017.
* at 11:27 PM
*/

interface DataResponse{

    @GET("posts/")
    fun getAllPostResponse(@Query("per_page")int: Int): Call<List<Post>>

    companion object {
        val retrofitBaseUrl:String = "https://shokherschool.com/wp-json/wp/v2/"
    }
}