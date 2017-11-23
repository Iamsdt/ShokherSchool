package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 11/15/2017.
 */

@Entity
class Categories(var count: Int? = 0,
                 var link: String? = "",
                 var name: String? = "",
                 var description: String? = "",
                 @PrimaryKey()
                 var id: Int? = 0)