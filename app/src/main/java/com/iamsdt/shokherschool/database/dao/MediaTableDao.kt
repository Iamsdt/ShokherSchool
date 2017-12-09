package com.iamsdt.shokherschool.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.MediaTable

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 7:27 PM
 */

@Dao
interface MediaTableDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(media: MediaTable):Long

    @get:Query("Select * From MediaTable")
    val getAllMedia: List<MediaTable>

    @Query("Select id From MediaTable")
    fun getMediaIds():List<Int>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select id From MediaTable where id = :arg0")
    fun getMediaID(arg0:Int):Int

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select title From MediaTable where id = :arg0")
    fun getMediaTitle(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select full From MediaTable where id = :arg0")
    fun getMediaFull(arg0:Int):String


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select thumbnail From MediaTable where id = :arg0")
    fun getMediaThumbnail(arg0:Int):String

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select medium From MediaTable where id = :arg0")
    fun getMediaMedium(arg0:Int):String

    @Delete
    fun deleteAllData(media: MediaTable)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(media: MediaTable)
}