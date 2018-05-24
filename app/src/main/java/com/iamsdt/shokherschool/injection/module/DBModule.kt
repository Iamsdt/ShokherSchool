package com.iamsdt.shokherschool.injection.module

import android.content.Context
import com.iamsdt.shokherschool.data.database.MyDatabase
import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides


/**
 * Created by Shudipto Trafder on 12/30/2017.
 * at 4:27 PM
 */
@Module(includes = [ContextModule::class])
class DBModule{

    @Provides
    @ApplicationScope
    fun getTagTableDao(myDatabase: MyDatabase):TagTableDao
            = myDatabase.tagTableDao

    @Provides
    @ApplicationScope
    fun getPageTableDao(myDatabase: MyDatabase):PageTableDao
            = myDatabase.pageTableDao

    @Provides
    @ApplicationScope
    fun getCategoriesTableDao(myDatabase: MyDatabase):CategoriesTableDao
            = myDatabase.categoriesTableDao

    @Provides
    @ApplicationScope
    fun getPostTableDao(myDatabase: MyDatabase):PostTableDao
            = myDatabase.postTableDao

    @Provides
    @ApplicationScope
    fun getAuthorTableDao(myDatabase: MyDatabase): AuthorTableDao
            = myDatabase.authorTableDao

    @Provides
    @ApplicationScope
    fun getDatabase(context: Context):MyDatabase
            = MyDatabase.getInstance(context)

}