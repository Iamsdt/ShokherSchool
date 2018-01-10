package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 11/15/2017.
 * at 5:22 PM
 */

@Entity
class CategoriesTable(var categories_count: Int? = 0,
                      var categories_link: String? = "",
                      var categories_name: String? = "",
                      var categories_description: String? = "",
                      @PrimaryKey
                      var categories_id: Int? = 0)