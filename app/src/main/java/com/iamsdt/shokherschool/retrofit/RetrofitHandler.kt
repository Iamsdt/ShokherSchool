package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.author.AuthorResponse
import com.iamsdt.shokherschool.retrofit.pojo.media.MediaResponse
import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse

/**
* Created by Shudipto Trafder on 11/19/2017.
*/
class RetrofitHandler(private val wpRestInterface: WPRestInterface) {

    fun getAllPostList():RetrofitLiveData<List<PostResponse>> =
            RetrofitLiveData(wpRestInterface.getAllPostList())

    fun getFilterPostList(filter: String):RetrofitLiveData<List<PostResponse>> =
            RetrofitLiveData(wpRestInterface.getFilterPostList(filter))

    fun getMediaByID(id:Int):RetrofitLiveData<MediaResponse> =
            RetrofitLiveData(wpRestInterface.getMediaByID(id))

    fun getAuthorByID(id:Int):RetrofitLiveData<AuthorResponse> =
            RetrofitLiveData(wpRestInterface.getAuthorByID(id))
}