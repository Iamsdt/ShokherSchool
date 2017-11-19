package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.iamsdt.shokherschool.database.MyDatabase
import com.iamsdt.shokherschool.retrofit.DataResponse
import com.iamsdt.shokherschool.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.retrofit.RetrofitLiveData
import com.iamsdt.shokherschool.retrofit.pojo.post.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
* Created by Shudipto Trafder Trafder on 11/17/2017.
*/

class MainViewModel(application: Application):AndroidViewModel(application){

    var allPost: RetrofitLiveData<List<Post>> ?= null
    private var myDatabase: MyDatabase?= null
    private var retrofit:Retrofit ?= null

    init {
        //myDatabase = MyDatabase.getInstance(application.applicationContext)
        retrofit = Retrofit.Builder().baseUrl(DataResponse.retrofitBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val dataResponse = retrofit!!.create(DataResponse::class.java)

        allPost = RetrofitHandler(dataResponse).getAllPostData()

    }

    override fun onCleared() {
        super.onCleared()
        allPost!!.cancel()
    }

}