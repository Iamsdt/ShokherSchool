package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder Trafder on 11/15/2017.
*/

@Entity
class Page (){

    @PrimaryKey
    var page_id:Int ?= null

    var page_link:String ?= null
    var page_title:String ?= null
    var page_author_id:Int ?= null
    var page_media_id:Int ?= null

    constructor(page_id: Int?,
                page_link: String?,
                page_title: String?,
                page_author_id: Int?,
                page_media_id: Int?):this() {

        this.page_id = page_id
        this.page_link = page_link
        this.page_title = page_title
        this.page_author_id = page_author_id
        this.page_media_id = page_media_id
    }
}
