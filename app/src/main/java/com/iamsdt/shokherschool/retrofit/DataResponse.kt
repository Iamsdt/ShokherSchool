package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
* Created by Shudipto Trafder on 11/18/2017.
* at 11:27 PM
*/

interface DataResponse{

    @GET("posts/")
    fun getAllPostResponse(): Call<List<PostResponse>>

    @GET("posts/")
    fun getSegmentedPost(@Query("before") filter: String): Call<List<PostResponse>>

    companion object {
        val retrofitBaseUrl:String = "https://shokherschool.com/wp-json/wp/v2/"
        val sampleDate = "2017-01-26T23:32:00"
    }
}