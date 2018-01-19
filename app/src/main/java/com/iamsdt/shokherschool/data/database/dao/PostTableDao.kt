package com.iamsdt.shokherschool.data.database.dao

import android.arch.persistence.room.*
import com.iamsdt.shokherschool.data.database.table.PostTable
import com.iamsdt.shokherschool.data.model.DetailsPostModel
import com.iamsdt.shokherschool.data.model.PostModel

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 7:04 PM
 */

@Dao
interface PostTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostTable):Long

    @get:Query("Select * From PostTable order by PostTable.post_date DESC LIMIT 10")
    val getFirst10DataList:List<PostTable>

    /**
     * This sql command also can be done by using inner join
     * here is code
     *
    SELECT PostTable.post_id AS id, PostTable.post_title as title, PostTable.post_date as date, AuthorTable.author_name as name, MediaTable.media_thumbnail_pic as thumb
    FROM ((PostTable inner join AuthorTable on PostTable.post_authorID = AuthorTable.author_id)
    inner join MediaTable on PostTable.post_featuredMediaID = MediaTable.media_id) order by PostTable.post_date desc
     */

    @get:Query("SELECT PostTable.post_id AS id," +
            " PostTable.post_date AS date," +
            " PostTable.post_title AS title," +
            " AuthorTable.author_name AS author," +
            " PostTable.media_medium_pic AS mediaLink," +
            " PostTable.bookmark AS bookmark" +
            " FROM PostTable, AuthorTable" +
            " WHERE PostTable.post_authorID = AuthorTable.author_id" +
            " order by PostTable.post_date DESC")
    val getPostData:List<PostModel>

    @Query("SELECT PostTable.post_id AS id," +
            " PostTable.post_date AS date," +
            " PostTable.post_title AS title," +
            " PostTable.postContent AS content," +
            " AuthorTable.author_name AS authorName," +
            " AuthorTable.author_description AS authorDetails," +
            " AuthorTable.author_avatarUrl24 AS authorImg," +
            " PostTable.media_medium_pic AS mediaLink," +
            " PostTable.tags As tags," +
            " PostTable.postCategories as categories" +
            " FROM PostTable, AuthorTable" +
            " WHERE PostTable.post_authorID = AuthorTable.author_id" +
            " And PostTable.post_id = :arg0 "+
            " order by PostTable.post_date DESC")
    fun getSinglePostDetails(arg0:Int):DetailsPostModel

    @Query("SELECT PostTable.post_id AS id," +
            " PostTable.post_date AS date," +
            " PostTable.post_title AS title," +
            " AuthorTable.author_name AS author," +
            " PostTable.media_medium_pic AS mediaLink," +
            " PostTable.bookmark AS bookmark" +
            " FROM PostTable, AuthorTable" +
            " WHERE PostTable.post_authorID = AuthorTable.author_id" +
            " And PostTable.bookmark = 1"+
            " order by PostTable.post_date DESC")
    fun getBookmarkData():List<PostModel>


    @Query("update PostTable set bookmark = 1 where PostTable.post_id = :arg0")
    fun setBookmark(arg0: Int):Int

    @Query("update PostTable set bookmark = 0 where PostTable.post_id = :arg0")
    fun deleteBookmark(arg0: Int):Int

    @Delete
    fun delete(post: PostTable):Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(post: PostTable):Int

}