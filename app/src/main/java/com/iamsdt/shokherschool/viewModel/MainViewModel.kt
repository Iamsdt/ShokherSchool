package com.iamsdt.shokherschool.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.iamsdt.shokherschool.database.MyDatabase
import com.iamsdt.shokherschool.database.table.Post

/**
* Created by Shudipto Trafder Trafder on 11/17/2017.
*/

class MainViewModel(application: Application):AndroidViewModel(application){

    var allPost: LiveData<List<Post>>?= null
    private var myDatabase: MyDatabase?= null

    init {
        myDatabase = MyDatabase.getInstance(application.applicationContext)
    }
}