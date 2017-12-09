package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.AuthorTable

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface AuthorTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(AuthorTable: AuthorTable):Long

    @Query("Select * From AuthorTable")
    fun getAllData() : LiveData<List<AuthorTable>>

    @Query("Select * From AuthorTable where AuthorTable.id = :arg0")
    fun getAuthorDetails(arg0:Int):List<AuthorTable>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select avatarUrl24 From AuthorTable where AuthorTable.id = :arg0")
    fun getAvatarUrl24(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select avatarUrl48 From AuthorTable where AuthorTable.id = :arg0")
    fun getAvatarUrl48(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select avatarUrl96 From AuthorTable where AuthorTable.id = :arg0")
    fun getAvatarUrl96(arg0:Int):String


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select name From AuthorTable where AuthorTable.id = :arg0")
    fun getAuthorName(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select id From MediaTable where id = :arg0")
    fun getAuthorID(arg0:Int):Int

    @Delete
    fun deleteAllData(AuthorTable: AuthorTable):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(AuthorTable: AuthorTable):Int

}