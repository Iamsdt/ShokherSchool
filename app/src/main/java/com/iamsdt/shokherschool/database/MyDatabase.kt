package com.iamsdt.shokherschool.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.database.table.AuthorTable
import com.iamsdt.shokherschool.database.table.MediaTable
import com.iamsdt.shokherschool.database.table.PostTable

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Database(entities = arrayOf(PostTable::class,AuthorTable::class
        ,MediaTable::class),
        version = 2,exportSchema = false)
abstract class MyDatabase:RoomDatabase() {


    //dao
    abstract val postTableDao:PostTableDao
    abstract val mediaTableDao:MediaTableDao
    abstract val authorTableDao:AuthorTableDao


    companion object {
        private var instance:MyDatabase ?= null
        private val dbName = "ShokerSchool"

        fun getInstance(context: Context):MyDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(context,
                        MyDatabase::class.java,dbName).build()
            }

            return instance!!
        }
    }

}