package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Entity
data class Page (@PrimaryKey val id: Int,
            val date: String,
            val template: String,
            val parent: Int,
            val author: Int,
            val link: String,
            val type: String,
            val title: String,
            val modified: String,
            val slug: String,
            val status: String)
