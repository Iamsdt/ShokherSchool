package com.iamsdt.shokherschool.data.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.data.database.table.CategoriesTable

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface CategoriesTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: CategoriesTable):Long

    //complete convert live data
    @get:Query("Select * From CategoriesTable")
    val getAllData: List<CategoriesTable>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categories: CategoriesTable):Int

    @Delete
    fun delete(categories: CategoriesTable):Int
}