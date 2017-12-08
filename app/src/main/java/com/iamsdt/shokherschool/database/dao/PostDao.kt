package com.iamsdt.shokherschool.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.iamsdt.shokherschool.database.table.Post

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 7:04 PM
 */

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post):Long

    //complete convert live data
    @get:Query("Select * From Post")
    val getAllData : LiveData<List<Post>>

    @get:Query("Select * From Post")
    val getAllData2:List<Post>

    @Query("Select * From Post where id = :arg0")
    fun getSinglePost(arg0:Int):List<Post>

    @Delete
    fun deleteAllData(post: Post):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(post: Post):Int

}