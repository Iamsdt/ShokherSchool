package com.iamsdt.shokherschool.database

import android.content.Context

/**
 * Created by Shudipto Trafder on 11/27/2017.
 * at 11:21 PM
 */
class DataSaver(val context: Context){

    var myDatabase:MyDatabase ?= null

    init {
        myDatabase = MyDatabase.getInstance(context)
    }

    fun saveAuthor(){

    }

}