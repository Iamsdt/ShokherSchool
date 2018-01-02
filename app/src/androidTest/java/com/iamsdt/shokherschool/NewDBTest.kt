package com.iamsdt.shokherschool

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.iamsdt.shokherschool.database.MyDatabase
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.database.table.AuthorTable
import com.iamsdt.shokherschool.database.table.MediaTable
import com.iamsdt.shokherschool.database.table.PostTable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Shudipto Trafder on 1/2/2018.
 * at 12:10 AM
 */

//this class is for test database
// after redesign database

@RunWith(AndroidJUnit4::class)
class NewDBTest{

    private var postDao: PostTableDao? = null
    private var medaiDao: MediaTableDao? = null
    private var authorDao: AuthorTableDao? = null

    @Before
    fun setup() {
        val database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyDatabase::class.java).allowMainThreadQueries().build()

        postDao = database.postTableDao
        medaiDao = database.mediaTableDao
        authorDao = database.authorTableDao
    }

    @Test
    fun insertData() {

        val post1 = PostTable(1,"12/15/17",1,"New post",1)
        val pos2 = PostTable(2,"12/15/17",1,"New post",2)

        val media1 = MediaTable(1,"profile","thumb","","")
        val media2 = MediaTable(2,"profile2","thumb2","","")

        val author1 = AuthorTable("","","","Shudipto","link1","author1",1)
        val author2 = AuthorTable("","","","Trafder","link2","author2",2)

        val po:ArrayList<Long> = ArrayList()
        val me:ArrayList<Long> = ArrayList()
        val au:ArrayList<Long> = ArrayList()

        //post insert
        po.add(postDao!!.insert(post1))
        po.add(postDao!!.insert(pos2))

        //media insert
        me.add(medaiDao!!.insert(media1))
        me.add(medaiDao!!.insert(media2))

        //author insert
        au.add(authorDao!!.insert(author1))
        au.add(authorDao!!.insert(author2))


        val pS = po.size
        val mS = me.size
        val aS = au.size

        //now access
        //val post = postDao!!.getPostData()

//        val size = post.value?.size
//
//        for (n in post.value!!){
//            val p = n.date
//            val i = n.id
//            val data = n.mediaLink
//        }

    }
}