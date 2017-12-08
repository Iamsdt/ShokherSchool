package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder on 11/15/2017.
* at 5:22 PM
*/

@Entity
class Author(var avatarUrls: String? = "",
             var name: String? = "",
             var link: String? = "",
             var description: String? = "",
             @PrimaryKey()
             var id: Int? = 0)