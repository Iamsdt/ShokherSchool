package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 11/23/2017.
 * at 6:47 PM
 */

@Entity
class Post(var date: String? = "",
           var author: Int? = 0,
           var link: String? = "",
           var title: String? = "",
           var commentStatus: String? = "",
           var featuredMedia: Int? = 0,
           @PrimaryKey()
           var id: Int? = 0,
           var categories: String? = "")