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
    fun insert(tag: TagTable): Long

    @get:Query("Select * From TagTable")
    val allData: List<TagTable>

    /**
     * Get tag name according to ID
     * @param arg0 int id
     * @return name of the tag in String
     */
    @Query("Select TagTable.tag_name From TagTable where TagTable.tag_id = :arg0")
    fun getTagName(arg0:Int):String

    @Delete
    fun delete(tag: TagTable): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(tag: TagTable) : Int

}