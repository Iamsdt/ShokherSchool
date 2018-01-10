package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 6:47 PM
 */

@Entity
class PostTable(@PrimaryKey
                var post_id: Int = 0,
                var post_date: String? = "",
                @ForeignKey(entity = AuthorTable::class,
                        parentColumns = ["AuthorTable.author_id"],
                        childColumns = ["PostTable.post_authorID"])
                var post_authorID: Int? = 0,
                var post_title: String? = "",
                var postContent:String ?= "",
                var postCatagories:String ?= "",
                var tags:String ?= "",
                var commentStatus:String ?= "",
                @Embedded
                var mediaTable:MediaTable ?= MediaTable())