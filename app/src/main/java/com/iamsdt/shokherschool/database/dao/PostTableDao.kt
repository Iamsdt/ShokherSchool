package com.iamsdt.shokherschool.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.PostTable

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 7:04 PM
 */

@Dao
interface PostTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostTable):Long

    @get:Query("Select * From PostTable order by date DESC")
    val getAllDataList:List<PostTable>

    @Query("Select * From PostTable where id = :arg0")
    fun getSinglePost(arg0:Int):List<PostTable>

    @Query("Select id From PostTable where id = :arg0")
    fun getPostID(arg0:Int):Int

    @Query("Select date From PostTable where id = :arg0")
    fun getPostDate(arg0:Int):String

    @Query("Select author From PostTable where id = :arg0")
    fun getPostAuthor(arg0:Int):Int

    @Query("Select featuredMedia From PostTable where id = :arg0")
    fun getPostMediaId(arg0:Int):Int

    @Delete
    fun deleteAllData(post: PostTable):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(post: PostTable):Int

}