package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.PageTable

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface PageTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(page: PageTable): Long


    @get:Query("Select * From PageTable")
    val allData: LiveData<List<PageTable>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select link From PageTable where id = :arg0")
    fun getPageTableLink(arg0: Int):List<String>


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select title From PageTable where id = :arg0")
    fun getPageTableTitle(arg0: Int):List<String>

    //complete join sql queries
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select Author.name as authorName From PageTable " +
            "LEFT JOIN Author ON PageTable.page_author_id = Author.authorID " +
            "where PageTable.page_id = :arg0")
    fun getPageTableAuthorName(arg0: Int):List<String>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select page_media_id From PageTable where id = :arg0")
    fun getPageTableMediaID(arg0: Int):List<Int>


    @Delete
    fun deleteAll(page: PageTable): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(page: PageTable) : Int
}
