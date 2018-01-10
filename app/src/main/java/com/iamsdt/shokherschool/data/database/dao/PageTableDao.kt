package com.iamsdt.shokherschool.data.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.data.database.table.PageTable

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface PageTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(page: PageTable): Long

    @get:Query("Select * From PageTable")
    val allData: List<PageTable>

    @Delete
    fun delete(page: PageTable): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(page: PageTable) : Int
}
