package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.Author

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface AuthorDao {

    @Insert
    fun insert(author: Author):Long

    //complete convert live data
    @get:Query("Select * From Author")
    val getAllData : LiveData<List<Author>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select author_name From Author where author_ID = :arg0")
    fun getAuthorName(arg0:Int):List<String>

    @Delete
    fun deleteAllData(author: Author):Int

    @Update
    fun update(author: Author):Int

}