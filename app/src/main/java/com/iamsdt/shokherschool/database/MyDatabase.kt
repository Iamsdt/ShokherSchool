package com.iamsdt.shokherschool.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.iamsdt.shokherschool.database.dao.AuthorDao
import com.iamsdt.shokherschool.database.dao.CategoriesDao
import com.iamsdt.shokherschool.database.dao.PageDao
import com.iamsdt.shokherschool.database.table.Author
import com.iamsdt.shokherschool.database.table.Categories
import com.iamsdt.shokherschool.database.table.Page

/**
* Created by Shudipto Trafder Trafder on 11/15/2017.
*/

@Database(entities = arrayOf(Categories::class,Page::class,Author::class),
        version = 1,exportSchema = false)
abstract class MyDatabase:RoomDatabase() {

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

    //dao
    abstract val categoriesDao:CategoriesDao
    abstract val pageDao:PageDao
    abstract val authorDao:AuthorDao

}