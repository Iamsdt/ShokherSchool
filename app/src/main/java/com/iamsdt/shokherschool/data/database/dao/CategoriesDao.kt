package com.iamsdt.shokherschool.data.database.dao

import android.arch.persistence.room.Dao

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Dao
interface CategoriesTableDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(categories: CategoriesTable):Long
//
//    //complete convert live data
//    @get:Query("Select * From CategoriesTable")
//    val getAllData: LiveData<List<CategoriesTable>>
//
//    @Query("Select * From CategoriesTable where id = :arg0")
//    fun getCategorize(arg0: Int):List<CategoriesTable>
//
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("Select name From CategoriesTable where id = :arg0")
//    fun getCategorizeName(arg0: Int):List<String>
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateCategoriesTable(categories: CategoriesTable):Int
//
//    @Delete
//    fun deleteAll(categories: CategoriesTable):Int
}