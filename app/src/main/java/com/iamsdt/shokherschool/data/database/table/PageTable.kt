package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder on 11/15/2017.
* at 5:22 PM
*/
@Entity
class PageTable(var pageTable_date: String = "",
                var pageTable_author: Int = 0,
                var pageTable_title: String ?= "",
                var pageTable_content: String ?= "",
                var pageTable_featuredMedia: Int = 0,
                @PrimaryKey
                var pageTable_id: Int = 0)
