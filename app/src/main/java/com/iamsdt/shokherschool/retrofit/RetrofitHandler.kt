package com.iamsdt.shokherschool.retrofit

import com.iamsdt.shokherschool.retrofit.pojo.post.Post

/**
* Created by Shudipto Trafder Trafder on 11/19/2017.
*/
class RetrofitHandler(private val dataResponse: DataResponse) {

    fun getAllPostData():RetrofitLiveData<List<Post>> =
            RetrofitLiveData(dataResponse.getAllPostResponse())

}