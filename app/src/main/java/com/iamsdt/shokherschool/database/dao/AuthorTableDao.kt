package com.iamsdt.shokherschool.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.AuthorTable

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface AuthorTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authorTable: AuthorTable):Long

    @Query("Select * From AuthorTable where AuthorTable.author_id = :arg0")
    fun getAuthorDetails(arg0:Int):List<AuthorTable>

    @Query("Select author_id From AuthorTable")
    fun getAuthorIds():List<Int>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select author_avatarUrl24 From AuthorTable where AuthorTable.author_id = :arg0")
    fun getAvatarUrl24(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select author_avatarUrl48 From AuthorTable where AuthorTable.author_id = :arg0")
    fun getAvatarUrl48(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select author_avatarUrl96 From AuthorTable where AuthorTable.author_id = :arg0")
    fun getAvatarUrl96(arg0:Int):String


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select author_name From AuthorTable where AuthorTable.author_id = :arg0")
    fun getAuthorName(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select author_id From AuthorTable where AuthorTable.author_id = :arg0")
    fun getAuthorID(arg0:Int):Int

    @Delete
    fun deleteAllData(AuthorTable: AuthorTable):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(AuthorTable: AuthorTable):Int

}