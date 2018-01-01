package com.iamsdt.shokherschool

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.iamsdt.shokherschool.database.MyDatabase
import com.iamsdt.shokherschool.database.dao.PostTableDao
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Shudipto Trafder on 12/3/2017.
 * at 11:57 PM
 */

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private var postDao: PostTableDao? = null

    @Before
    fun setup() {
        val database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyDatabase::class.java).allowMainThreadQueries().build()

        postDao = database.postTableDao
    }

    @Test
    fun insertData() {

        //change the database design so that
        // comment out this code

//        val author1 = PostTable(id = 1)
//        val author2 = PostTable(id = 2)
//        val author3 = PostTable(id = 3)
//        val author4 = PostTable(id = 4)
//
//        val list:ArrayList<Long> = ArrayList()
//
//        list.add(postDao!!.insert(author1))
//        list.add(postDao!!.insert(author2))
//        list.add(postDao!!.insert(author3))
//        list.add(postDao!!.insert(author4))


        val data = postDao!!.getAllDataList
    }
}