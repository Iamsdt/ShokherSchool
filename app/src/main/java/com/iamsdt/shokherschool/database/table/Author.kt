package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder Trafder on 11/15/2017.
*/

@Entity
class Author(){

    @PrimaryKey
    var author_ID:Int ?= null
    var author_name:String ?= null

    constructor(author_ID: Int?, author_name: String?):this() {
        this.author_ID = author_ID
        this.author_name = author_name
    }
}