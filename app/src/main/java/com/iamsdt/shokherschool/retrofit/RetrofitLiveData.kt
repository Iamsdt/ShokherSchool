package com.iamsdt.shokherschool.retrofit

import android.arch.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
* Created by Shudipto Trafder on 11/19/2017.
*/
class RetrofitLiveData<T>(private val call: Call<T>) : MutableLiveData<T>(), Callback<T> {

    override fun onActive() {
        if (!call.isCanceled && !call.isExecuted) call.enqueue(this)
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        //not implemented
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        value = response?.body()
    }

    fun cancel() = if(!call.isCanceled) call.cancel() else Unit
}