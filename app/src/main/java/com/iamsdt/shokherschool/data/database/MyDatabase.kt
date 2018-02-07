package com.iamsdt.shokherschool.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.data.database.table.*

/**
 * Created by Shudipto Trafder Trafder on 11/15/2017.
 * at 10:42 PM
 */

@Database(entities = [PostTable::class,
    AuthorTable::class, PageTable::class,
    CategoriesTable::class,
    TagTable::class],
        version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {


    //dao
    abstract val postTableDao: PostTableDao
    abstract val authorTableDao: AuthorTableDao
    abstract val categoriesTableDao: CategoriesTableDao
    abstract val pageTableDao: PageTableDao
    abstract val tagTableDao: TagTableDao


    companion object {
        private const val dbName = "ShokerSchool"

        /**
         * Get instance of Database
         * @param context of application
         * @return instance of database
         */
        fun getInstance(context: Context): MyDatabase =
                Room.databaseBuilder(context,
                        MyDatabase::class.java, dbName).build()

    }

}