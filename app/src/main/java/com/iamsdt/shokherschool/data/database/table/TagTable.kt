package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 1/10/2018.
 * at 8:05 PM
 */
@Entity
class TagTable(var tag_count: Int = 0,
               var tag_name: String ?= "",
               @PrimaryKey
               var tag_id: Int = 0)