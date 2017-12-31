package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import org.jsoup.Jsoup
import java.util.concurrent.Executors

/**
* Created by Shudipto Trafder on 11/21/2017.
* at 11:20 PM
*/
class DetailsViewModel(application: Application):
        AndroidViewModel(application) {

    private var htmlData:MutableLiveData<String> ?= null

    init {
        htmlData = MutableLiveData()
    }

    fun getHtmlData(postID:Int):MutableLiveData<String>?{

        if (htmlData == null || htmlData!!.value.isNullOrEmpty()){
            initializeData(postID)
        }

        return htmlData
    }


    private fun initializeData(id:Int){
        val execute = Executors.newSingleThreadExecutor()
        execute.submit({
            val postLink = "https://shokherschool.com/?p=$id"

            val htmlDocument = Jsoup.connect(postLink).get()

            val element = htmlDocument
                    .getElementsByClass("entry-content")

            // replace with selected element
            //because we need theme of
            htmlDocument.empty().append(element.toString())

            //now remove social content
            htmlDocument.getElementsByClass("apss-social-share")
                    .first().remove()

            htmlData!!.postValue(htmlDocument.toString())
        })
    }
}