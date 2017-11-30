package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.author.AuthorResponse
import com.iamsdt.shokherschool.retrofit.pojo.categories.CategoriesResponse
import com.iamsdt.shokherschool.retrofit.pojo.page.PageResponse
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse
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

    @GET("posts")
    fun getCategoriesPost(@Query("categories") id: Int): Call<List<PostResponse>>

    /**
     * Gets all Posts created by a User.
     *
     * @param authorId Id of the User
     * @return List of Post objects for the User
     */
    @GET("posts")
    fun getPostsForAuthor(@Query("author") authorId: Long): Call<List<AuthorResponse>>


    //get categories data
    @GET("categories?&per_page=100")
    fun getAllCategories(): Call<List<CategoriesResponse>>

    //getting user or author
    @GET("users")
    fun getAllUser() : Call<List<AuthorResponse>>


    //getting comment of a post
//    @GET("comments?&post={id}")
//    fun getAllCommentOfAPost(@Path("id") int: Int): Call<List<CategoriesResponse>>


    /* PAGES */

    @GET("pages")
    fun getPages(): Call<List<PageResponse>>

    @GET("pages/{pageId}")
    fun getPageUsingID(@Path("pageId") pageId: Long): Call<PageResponse>


    /**
     * Returns a single Media item.
     *
     * @param mediaId Id of the Media item
     * @return The Media object
     */
    @GET("media/{id}")
    fun getMediaByID(@Path("id") mediaId: Long): Call<String>


    @GET("categories/{id}")
    fun getCategoryByID(@Path("id") id: Long): Call<CategoriesResponse>


    //fixme 11/27/2017 create comment option later
//    /* COMMENTS */
//
//    @POST("comments")
//    fun createComment(fields: Map<String, Any>): Call<String>
//
//    @GET("comments")
//    fun getComments(): Call<List<Comment>>
//
//    @GET("comments/{id}")
//    fun getComment(@Path("id") id: Long): Call<Comment>
//
//    @POST("comments/{id}")
//    fun updateComment(@Path("id") id: Long, fields: Map<String, Any>): Call<Comment>
//
//    @DELETE("comments/{id}")
//    fun deleteComment(@Path("id") id: Long): Call<Comment>
}