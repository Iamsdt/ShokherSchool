package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.Author

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(author: Author):Long

    //complete convert live data
    @get:Query("Select * From Author")
    val getAllData : LiveData<List<Author>>

    @Query("Select * From Author where Author.id = :arg0")
    fun getAuthorDetails(arg0:Int):List<Author>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select avatarUrls From Author where Author.id = :arg0")
    fun getAvatarUrls(arg0:Int):List<String>


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select name From Author where Author.id = :arg0")
    fun getAuthorName(arg0:Int):List<String>

    @Delete
    fun deleteAllData(author: Author):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(author: Author):Int

}