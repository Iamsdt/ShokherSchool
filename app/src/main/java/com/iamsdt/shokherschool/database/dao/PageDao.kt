package com.iamsdt.shokherschool.database.dao

import android.arch.persistence.room.Dao

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface PageDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(page: Page): Long
//
//    //complete convert live data
//    @get:Query("Select * From Page")
//    val allData: LiveData<List<Page>>
//
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("Select link From Page where id = :arg0")
//    fun getPageLink(arg0: Int):List<String>
//
//
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("Select title From Page where id = :arg0")
//    fun getPageTitle(arg0: Int):List<String>
//
//    //complete join sql queries
////    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
////    @Query("Select Author.name as authorName From Page " +
////            "LEFT JOIN Author ON Page.page_author_id = Author.authorID " +
////            "where Page.page_id = :arg0")
////    fun getPageAuthorName(arg0: Int):List<String>
//
////    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
////    @Query("Select page_media_id From Page where id = :arg0")
////    fun getPageMediaID(arg0: Int):List<Int>
//
//
//    @Delete
//    fun deleteAll(page: Page): Int
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun update(page: Page) : Int
}
