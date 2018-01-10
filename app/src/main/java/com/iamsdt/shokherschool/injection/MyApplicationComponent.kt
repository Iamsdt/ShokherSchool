package com.iamsdt.shokherschool.injection

import com.iamsdt.shokherschool.data.database.dao.*
import com.iamsdt.shokherschool.data.retrofit.RetrofitHandler
import com.iamsdt.shokherschool.data.retrofit.WPRestInterface
import com.iamsdt.shokherschool.injection.module.DBModule
import com.iamsdt.shokherschool.injection.module.PicassoModule
import com.iamsdt.shokherschool.injection.module.RetrofitModule
import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
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
    val categoriesTableDao:CategoriesTableDao
    val pageTableDao:PageTableDao
    val tagTableDao:TagTableDao
}