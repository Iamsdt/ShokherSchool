package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 7:23 PM
 */

@Entity
class MediaTable(
        @PrimaryKey
        var id: Int ?= 0,
        var title: String ?= "",
        var thumbnail: String?= "",
        var medium: String?= "",
        var full: String?= "")