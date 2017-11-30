package com.iamsdt.shokherschool.database

import android.content.Context
import com.iamsdt.shokherschool.database.table.Author
import com.iamsdt.shokherschool.database.table.Page
import com.iamsdt.shokherschool.retrofit.RetrofitData
import com.iamsdt.shokherschool.retrofit.WPRestInterface

/**
 * Created by Shudipto Trafder on 11/27/2017.
 * at 11:21 PM
 */
class DataSaver(val context: Context) {

    var myDatabase: MyDatabase? = null

    var dataResponse: WPRestInterface? = null

    init {
        myDatabase = MyDatabase.getInstance(context)
        dataResponse = RetrofitData().wpRestInterface

        //save author
        //saveAuthor()

        //save categories
        saveCategories()

        //save page
        savePage()

    }

    private fun saveCategories() {
        val data = dataResponse!!.getAllCategories().execute()
        if (data.isSuccessful){

        }
    }

    private fun savePage() {
        val data = dataResponse!!.getPages().execute()
        if (data.isSuccessful) {
            data.body()!!
                    .map {
                        Page(it.date, it.template, it.parent, it.author,
                                it.link, it.type, it.title!!.rendered, it.modified,
                                it.id, it.slug, it.status)
                    }
                    .forEach { myDatabase!!.pageDao.insert(it) }


        }
    }

    private fun saveAuthor(){
        val user = dataResponse!!.getAllUser().execute()

        if (user.isSuccessful) {
            user.body()!!.map { Author(it.avatarUrls!!.avatar48,it.name,
                    it.link,it.description,it.id) }
                    .forEach { myDatabase!!.authorDao.insert(it) }
        }

    }
}