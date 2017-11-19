package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.Page

/**
* Created by Shudipto Trafder Trafder Trafder on 11/15/2017.
*/

@Dao
interface PageDao {

    @Insert
    fun insert(page: Page): Long

    //complete convert live data
    @get:Query("Select * From Page")
    val allData: LiveData<List<Page>>

//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("Select page_link From Page where page_id = :int")
//    fun getPageLink(int: Int):List<String>
//
//
//    @Query("Select page_title From Page where page_id = :int")
//    fun getPageTitle(int: Int):List<Page>
//
//    //complete join sql queries
//    @Query("Select Author.author_name as authorName From Page " +
//            "LEFT JOIN Author ON Page.page_author_id = Author.author_ID " +
//            "where Page.page_id = :int")
//    fun getPageAuthorName(int: Int):List<Page>
//
//    @Query("Select page_media_id From Page where page_id = :int")
//    fun getPageMediaID(int: Int):List<Page>


    @Delete
    fun deleteAll(vararg page: Page): Int

    @Update
    fun update(page: Page)
}
