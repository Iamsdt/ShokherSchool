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

    /**
     * Get categories Name
     * @param arg0 id of the particular category
     *
     * @return name of the category in string*/
    @Query("Select CategoriesTable.categories_name From CategoriesTable where CategoriesTable.categories_id = :arg0")
    fun getCategoriesName(arg0:Int):String

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categories: CategoriesTable):Int

    @Delete
    fun delete(categories: CategoriesTable):Int
}