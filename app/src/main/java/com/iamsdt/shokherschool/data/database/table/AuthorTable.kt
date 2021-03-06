package com.iamsdt.shokherschool.data.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shudipto Trafder on 11/15/2017.
 * at 5:22 PM
 */

@Entity
class AuthorTable(
        var author_avatarUrl24: String? = "",
        var author_avatarUrl48: String? = "",
        var author_avatarUrl96: String? = "",
        var author_name: String? = "",
        var author_link: String? = "",
        var author_description: String? = "",
        @PrimaryKey
        var author_id: Int? = 0)