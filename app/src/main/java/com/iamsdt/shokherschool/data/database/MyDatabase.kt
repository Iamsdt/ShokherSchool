package com.iamsdt.shokherschool.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.iamsdt.shokherschool.data.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.data.database.dao.CategoriesTableDao
import com.iamsdt.shokherschool.data.database.dao.PageTableDao
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.table.AuthorTable
import com.iamsdt.shokherschool.data.database.table.CategoriesTable
import com.iamsdt.shokherschool.data.database.table.PageTable
import com.iamsdt.shokherschool.data.database.table.PostTable

/**
 * Created by Shudipto Trafder Trafder on 11/15/2017.
 * at 10:42 PM
 */

@Database(entities = [PostTable::class,
    AuthorTable::class, PageTable::class,CategoriesTable::class],
        version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {


    //dao
    abstract val postTableDao: PostTableDao
    abstract val authorTableDao: AuthorTableDao
    abstract val categoriesTableDao: CategoriesTableDao
    abstract val pageTableDao: PageTableDao



    companion object {
        private val dbName = "ShokerSchool"

        fun getInstance(context: Context): MyDatabase =
                Room.databaseBuilder(context,
                        MyDatabase::class.java, dbName).build()

    }

}