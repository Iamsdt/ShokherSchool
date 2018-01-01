package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 6:47 PM
 */

@Entity
class PostTable(@PrimaryKey
                var post_id: Int = 0,
                var post_date: String? = "",
                var post_authorID: Int? = 0,
                var post_author_Name:String ?= "",
                var post_title: String? = "",
                var post_featuredMediaID: Int? = 0,
                var post_featuredMedia_link:String ?= "")