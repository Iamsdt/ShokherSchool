package com.iamsdt.shokherschool.database


import android.net.Uri
import android.provider.BaseColumns
import android.provider.BaseColumns._ID

/**
* Created by Shudipto Trafder Trafder on 11/14/2017.
*/

class DBContract {

    //not sure
    //use or not
    internal companion object Contract{

        val AUTHORITY: String? = "com.iamsdt.shokherschool"

        val BASE_CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY)
    }

    //for table post
    object POST:BaseColumns {

        //table POST
        val TABLE_POST:String = "Post"
        val POST_ID = "post_id"
        val POST_DATE = "post_date"
        val POST_LINK = "post_link"
        val POST_TITLE = "post_title"
        val AUTHOR_ID = "author_id"
        val MEDIA_ID = "media_id"

        //uri to access database
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_POST).build()

        //SQl command
        val SQL_TABLE_POST = "CREATE TABLE $TABLE_POST ( " +
                "$_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "$POST_ID INTEGER NOT NULL, " +
                "$POST_DATE TEXT NOT NULL, " +
                "$POST_LINK TEXT, " +
                "$POST_TITLE TEXT, " +
                "$AUTHOR_ID INTEGER, " +
                "$MEDIA_ID INTEGER, "+
                "UNIQUE ($POST_TITLE) ON CONFLICT REPLACE));"


        /**
         * This methods for build uri for a particular row
         * @param id table row id
         */
        fun buildUriWithId(id: Int): Uri {
            return Author.CONTENT_URI.buildUpon().appendPath(id.toString()).build()

        }
    }

    //for table categories
    object Categories:BaseColumns {
        //table Categories
        val TABLE_CATEGORIES:String = "Categories"
        val CATEGORIES_ID = "categories_id"
        val COUNT_NUMBER = "count_number"
        val CATEGORIES_LINK = "categories_link"
        val CATEGORIES_NAME = "categories_name"

        //uri to access database
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_CATEGORIES).build()

        //SQL command
        val SQL_TABLE_CATEGORIES = "CREATE TABLE $TABLE_CATEGORIES ( " +
                "$_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "$CATEGORIES_ID INTEGER NOT NULL, " +
                "$COUNT_NUMBER INTEGER, " +
                "$CATEGORIES_LINK TEXT, " +
                "$CATEGORIES_NAME INTEGER," +
                " UNIQUE ($CATEGORIES_ID) ON CONFLICT REPLACE));"

        /**
         * This methods for build uri for a particular row
         * @param id table row id
         */

        fun buildUriWithId(id: Int): Uri {
            return Author.CONTENT_URI.buildUpon().appendPath(id.toString()).build()

        }
    }

    //for table categories
    object Page:BaseColumns {
        //table Categories
        val TABLE_PAGE:String = "page"
        val PAGE_ID = "page_id"
        val PAGE_LINK = "page_link"
        val PAGE_TITLE = "page_title"
        val AUTHOR_ID = "author_id"
        val MEDIA_ID = "media_id"

        //uri to access database
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_PAGE).build()

        //SQL command
        val SQL_TABLE_PAGE = "CREATE TABLE $TABLE_PAGE ( " +
                "$_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "$PAGE_ID INTEGER NOT NULL, " +
                "$PAGE_LINK TEXT, " +
                "$PAGE_TITLE TEXT, " +
                "$AUTHOR_ID INTEGER," +
                "$MEDIA_ID INTEGER," +
                " UNIQUE ($PAGE_ID) ON CONFLICT REPLACE));"

        /**
         * This methods for build uri for a particular row
         * @param id table row id
         */

        fun buildUriWithId(id: Int): Uri {
            return Author.CONTENT_URI.buildUpon().appendPath(id.toString()).build()

        }
    }

    //for table author
    object Author:BaseColumns {

        //table Categories
        val TABLE_AUTHOR:String = "author"
        val author_ID = "author_id"
        val author_name = "author_name"

        //uri to access database
        val CONTENT_URI: Uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_AUTHOR).build()

        //SQL command
        val SQL_TABLE_AUTHOR = "CREATE TABLE $TABLE_AUTHOR ( " +
                "$_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                "$author_ID INTEGER NOT NULL, " +
                "$author_name TEXT, " +
                " UNIQUE ($author_ID) ON CONFLICT REPLACE));"


        /**
         * This methods for build uri for a particular row
         * @param id table row id
         */

        fun buildUriWithId(id: Int): Uri {
            return CONTENT_URI.buildUpon().appendPath(id.toString()).build()

        }
    }

}