package com.iamsdt.shokherschool

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.iamsdt.shokherschool.data.database.MyDatabase
import com.iamsdt.shokherschool.data.database.dao.PostTableDao
import com.iamsdt.shokherschool.data.database.table.MediaTable
import com.iamsdt.shokherschool.data.database.table.PostTable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Shudipto Trafder on 1/12/2018.
 * at 5:49 PM
 */

@RunWith(AndroidJUnit4::class)
class DatabaseUpdateTest(){

    var postTableDao:PostTableDao ?= null

    @Before
    fun setUp(){
        val database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyDatabase::class.java).allowMainThreadQueries().build()

        postTableDao = database.postTableDao
    }

    @Test
    fun runUpdate(){
        val table = PostTable(10,"2014,15,16",1,"This is a post",
                "Content","1,2,3,4","1,3,4","0",0,MediaTable())
        postTableDao?.insert(table)

        //now update
        val table2 = PostTable(post_id = 10,bookmark = 1)
        val update = postTableDao?.update(table2)

        val bookmark = postTableDao?.getBookmarkData(1) ?: arrayListOf()

        for (b in bookmark){
            val id = b.id
            val book = b.author
            val data = b.title
        }
    }
}