package com.iamsdt.shokherschool.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.iamsdt.shokherschool.database.DBContract.Author.SQL_TABLE_AUTHOR
import com.iamsdt.shokherschool.database.DBContract.Categories.SQL_TABLE_CATEGORIES
import com.iamsdt.shokherschool.database.DBContract.POST.SQL_TABLE_POST
import com.iamsdt.shokherschool.database.DBContract.Page.SQL_TABLE_PAGE
import com.iamsdt.shokherschool.utilities.Utility

/**
* Created by Shudipto Trafder Trafder on 11/14/2017.
*/

class DBHelper(context:Context,
                DB_NAME:String = "ShokerSchool",
                DB_VERSION:Int = 1)

    :SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION){


    override fun onCreate(db: SQLiteDatabase?) {

        //create post table
        db!!.execSQL(SQL_TABLE_POST)

        //create Categories table
        db.execSQL(SQL_TABLE_CATEGORIES)

        //create page table
        db.execSQL(SQL_TABLE_PAGE)

        //create author table
        db.execSQL(SQL_TABLE_AUTHOR)

        Utility.logger("Db created","Database")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + SQL_TABLE_POST)
        db.execSQL("DROP TABLE IF EXISTS " + SQL_TABLE_CATEGORIES)
        db.execSQL("DROP TABLE IF EXISTS " + SQL_TABLE_PAGE)
        db.execSQL("DROP TABLE IF EXISTS " + SQL_TABLE_AUTHOR)

        //recreate database
        onCreate(db)

        Utility.logger("Db recreate","Database")
    }

}