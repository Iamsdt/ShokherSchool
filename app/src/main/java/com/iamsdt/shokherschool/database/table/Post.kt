package com.iamsdt.shokherschool.database.table

/**
* Created by Shudipto Trafder Trafder on 11/17/2017.
*/

//don't create table
// because don't save the post data
class Post(
        var postID:Int,
        var date:String,
        var title:String,
        var link:String,
        var authorID:Int,
        var mediaCount:Int,
        vararg categoriesID:Int)