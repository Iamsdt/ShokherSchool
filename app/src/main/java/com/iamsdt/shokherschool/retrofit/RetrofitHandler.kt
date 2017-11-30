package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.post.PostResponse

/**
* Created by Shudipto Trafder on 11/19/2017.
*/
class RetrofitHandler(private val wpRestInterface: WPRestInterface) {

    fun getAllPostData():RetrofitLiveData<List<PostResponse>> =
            RetrofitLiveData(wpRestInterface.getAllPostList())
}