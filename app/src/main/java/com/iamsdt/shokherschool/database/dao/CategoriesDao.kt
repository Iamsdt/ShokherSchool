package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.Categories

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: Categories):Long

    //complete convert live data
    @get:Query("Select * From Categories")
    val getAllData:LiveData<List<Categories>>

    @Query("Select * From Categories where id = :arg0")
    fun getCategorize(arg0: Int):List<Categories>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("Select name From Categories where id = :arg0")
    fun getCategorizeName(arg0: Int):List<String>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCategories(categories: Categories):Int

    @Delete
    fun deleteAll(categories: Categories):Int
}