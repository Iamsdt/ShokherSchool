package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder on 11/15/2017.
* at 5:22 PM
*/
@Entity
class PageTable(var date: String? = "",
           var template: String? = "",
           var parent: Int? = 0,
           var author: Int? = 0,
           var link: String? = "",
           var type: String? = "",
           var title: String? = "",
           var modified: String? = "",
           @PrimaryKey()
           var id: Int? = 0,
           var slug: String? = "",
           var status: String? = "")
