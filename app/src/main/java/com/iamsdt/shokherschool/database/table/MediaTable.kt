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
        var media_id: Int ?= 0,
        var media_title: String ?= "",
        var media_thumbnail_pic: String?= "",
        var media_medium_pic: String?= "",
        var media_full_pic: String?= "")