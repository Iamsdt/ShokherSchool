package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder Trafder on 11/15/2017.
*/

@Entity
class Categories() {

    @PrimaryKey
    var categories_id:Int ?= null

    var categories_count_number:String ?= null

    var categories_link:String ?= null

    var categories_name:String ?= null

    var categories_description:String ?= null

    constructor(categories_id: Int?,
                categories_count_number: String?,
                categories_link: String?,
                categories_name: String?,
                categories_description: String?):this() {
        this.categories_id = categories_id
        this.categories_count_number = categories_count_number
        this.categories_link = categories_link
        this.categories_name = categories_name
        this.categories_description = categories_description
    }
}