package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Entity
data class Categories(@PrimaryKey val id: Int,
                 val count: Int,
                 val link: String,
                 val name: String,
                 val description: String)