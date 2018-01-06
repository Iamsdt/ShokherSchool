package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import timber.log.Timber

/**
* Created by Shudipto Trafder on 11/21/2017.
* at 11:20 PM
*/
class DetailsViewModel(application: Application):
        AndroidViewModel(application) {

    private var htmlData:MutableLiveData<String> ?= null

    fun getHtmlData(boolean: Boolean,postID:Int):MutableLiveData<String>?{

        if (htmlData == null || htmlData!!.value.isNullOrEmpty()){

            htmlData = MutableLiveData()

            if (boolean){
                initializeData(postID)
            }
        }

        return htmlData
    }


    private fun initializeData(id:Int){

        AsyncTask.execute({
            val postLink = "https://shokherschool.com/?p=$id"

            Timber.i(postLink)

            htmlData!!.postValue("")
        })
    }
}