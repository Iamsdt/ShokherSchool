package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Entity

/**
* Created by Shudipto Trafder on 11/15/2017.
* at 5:22 PM
*/

@Entity(primaryKeys = ["categories_id"])
class CategoriesTable(var categories_count: Int? = 0,
                 var categories_link: String? = "",
                 var categories_name: String? = "",
                 var categories_description: String? = "",
                 var categories_id: Int? = 0)