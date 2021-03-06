package com.iamsdt.shokherschool.data.retrofit

import com.iamsdt.shokherschool.data.retrofit.pojo.author.AuthorResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.categories.CategoriesResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.comment.CommentResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.media.MediaResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.page.PageResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.post.PostResponse
import com.iamsdt.shokherschool.data.retrofit.pojo.tags.TagResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("pages")
    fun getPages(): Call<List<PageResponse>>

    @GET("categories?&per_page=100")
    fun getCategories(): Call<List<CategoriesResponse>>

    @GET("tags?&per_page=100")
    fun getTags(): Call<List<TagResponse>>

    @GET("comments?")
    fun getCommentForId(@Query("post") postId: Int): Call<List<CommentResponse>>

    @POST("comments")
    fun createComment(fields: Map<String, Any>): Call<String>

}