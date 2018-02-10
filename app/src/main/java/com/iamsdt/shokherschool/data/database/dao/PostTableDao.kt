package com.iamsdt.shokherschool.data.database.dao

import android.arch.lifecycle.LiveData
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
    fun insert(post: PostTable): Long

    /**
     * Get first ten post from database*/
    @get:Query("Select * From PostTable order by PostTable.post_date DESC LIMIT 10")
    val getFirst10DataList: List<PostTable>

    /**
     * This sql command also can be done by using inner join
     * here is code
     * SELECT PostTable.post_id AS id, PostTable.post_title as title, PostTable.post_date as date, AuthorTable.author_name as name, MediaTable.media_thumbnail_pic as thumb
    FROM ((PostTable inner join AuthorTable on PostTable.post_authorID = AuthorTable.author_id)
    inner join MediaTable on PostTable.post_featuredMediaID = MediaTable.media_id) order by PostTable.post_date desc
     */

    /**
     * Get List of Post Data
     * Select from two table PostTable AuthorTable
     * join condition PostTable.post_authorID = AuthorTable.author_id
     * */
    @get:Query("SELECT PostTable.post_id AS id," +
            " PostTable.post_date AS date," +
            " PostTable.post_title AS title," +
            " AuthorTable.author_name AS author," +
            " PostTable.media_medium_pic AS mediaLink," +
            " PostTable.bookmark AS bookmark" +
            " FROM PostTable, AuthorTable" +
            " WHERE PostTable.post_authorID = AuthorTable.author_id" +
            " order by PostTable.post_date DESC")
    val getPostData: LiveData<List<PostModel>>

    /**
     * Get details about a Single post
     * Select from two table PostTable AuthorTable
     * join condition PostTable.post_authorID = AuthorTable.author_id
     *
     * @return live data of Details post*/

    @Query("SELECT PostTable.post_id AS id," +
            " PostTable.post_date AS date," +
            " PostTable.post_title AS title," +
            " PostTable.postContent AS content," +
            " AuthorTable.author_name AS authorName," +
            " AuthorTable.author_description AS authorDetails," +
            " AuthorTable.author_avatarUrl96 AS authorImg," +
            " PostTable.media_medium_pic AS mediaLink," +
            " PostTable.tags As tags," +
            " PostTable.postCategories as categories, " +
            " PostTable.bookmark AS bookmark" +
            " FROM PostTable, AuthorTable" +
            " WHERE PostTable.post_authorID = AuthorTable.author_id" +
            " And PostTable.post_id = :arg0 " +
            " order by PostTable.post_date DESC")
    fun getSinglePostDetails(arg0: Int): LiveData<DetailsPostModel>

    /**
     * Get a list of bookmarked post
     * Select from two table PostTable and Author Table
     * join condition PostTable.post_authorID = AuthorTable.author_id
     *
     * @return live data with list of PostModel
     */

    @Query("SELECT PostTable.post_id AS id," +
            " PostTable.post_date AS date," +
            " PostTable.post_title AS title," +
            " AuthorTable.author_name AS author," +
            " PostTable.media_medium_pic AS mediaLink," +
            " PostTable.bookmark AS bookmark" +
            " FROM PostTable, AuthorTable" +
            " WHERE PostTable.post_authorID = AuthorTable.author_id" +
            " And PostTable.bookmark = 1" +
            " order by PostTable.post_date DESC")
    fun getBookmarkData(): LiveData<List<PostModel>>

    /**
     * Set bookmark
     * actually update bookmark 0 to 1
     * @param arg0 post id
     *
     * @return update status
     */
    @Query("update PostTable set bookmark = 1 where PostTable.post_id = :arg0")
    fun setBookmark(arg0: Int): Int

    /**
     * Delete from bookmark
     * actually update bookmark 1 to 0
     * @param arg0 post id
     *
     * @return update status
     */
    @Query("update PostTable set bookmark = 0 where PostTable.post_id = :arg0")
    fun deleteBookmark(arg0: Int): Int

    @Delete
    fun delete(post: PostTable): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(post: PostTable): Int

}