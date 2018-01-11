package com.iamsdt.shokherschool.data.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.data.database.table.TagTable

/**
 * Created by Shudipto Trafder on 1/10/2018.
 * at 8:09 PM
 */

@Dao
interface TagTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(page: TagTable): Long

    @get:Query("Select * From TagTable")
    val allData: List<TagTable>

    @Query("Select TagTable.tag_name From TagTable where TagTable.tag_id = :arg0")
    fun getTagName(arg0:Int):String

    @Delete
    fun delete(page: TagTable): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(page: TagTable) : Int

}