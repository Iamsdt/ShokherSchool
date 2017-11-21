package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.Categories

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface CategoriesDao {

    @Insert
    fun insert(categories: Categories):Long

    //complete convert live data
    @get:Query("Select * From Categories")
    val getAllData:LiveData<List<Categories>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select categories_link From Categories where categories_id = :arg0")
    fun getCategorieLink(arg0: Int):List<String>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select categories_name From Categories where categories_id = :arg0")
    fun getCategorieName(arg0: Int):List<String>


    @Update
    fun updateCategories(categories: Categories):Int

    @Delete
    fun deleteAll(vararg categories: Categories):Int
}