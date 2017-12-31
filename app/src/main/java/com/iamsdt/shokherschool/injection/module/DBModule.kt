package com.iamsdt.shokherschool.injection.module

import android.content.Context
import com.iamsdt.shokherschool.database.MyDatabase
import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.MediaTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
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
    fun getPostTableDao(myDatabase: MyDatabase):PostTableDao
            = myDatabase.postTableDao

    @Provides
    @ApplicationScope
    fun getMediaTableDao(myDatabase: MyDatabase): MediaTableDao
            = myDatabase.mediaTableDao

    @Provides
    @ApplicationScope
    fun getAuthorTableDao(myDatabase: MyDatabase): AuthorTableDao
            = myDatabase.authorTableDao

    @Provides
    @ApplicationScope
    fun getDatabase(context: Context):MyDatabase
            = MyDatabase.getInstance(context)

}