package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Entity

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 7:23 PM
 */

@Entity
class MediaTable(
        var media_id: Int ?= 0,
        var media_title: String ?= "",
        var media_medium_pic: String?= "")