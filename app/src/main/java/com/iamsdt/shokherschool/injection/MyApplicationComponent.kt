package com.iamsdt.shokherschool.injection

import com.iamsdt.shokherschool.database.dao.AuthorTableDao
import com.iamsdt.shokherschool.database.dao.PostTableDao
import com.iamsdt.shokherschool.injection.module.DBModule
import com.iamsdt.shokherschool.injection.module.PicassoModule
import com.iamsdt.shokherschool.injection.module.RetrofitModule
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import com.iamsdt.shokherschool.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.retrofit.WPRestInterface
import com.squareup.picasso.Picasso
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 9:22 PM
 */
@ApplicationScope
@Component(modules = [PicassoModule::class,RetrofitModule::class,
    DBModule::class])
interface MyApplicationComponent{

    //picasso
    val picasso:Picasso

    //retrofit
    val retrofitHandler:RetrofitHandler
    val wpRestInterface:WPRestInterface

    //database
    val postTableDao:PostTableDao
    val authorTableDao:AuthorTableDao
}