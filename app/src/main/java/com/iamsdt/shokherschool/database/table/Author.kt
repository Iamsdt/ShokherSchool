package com.iamsdt.shokherschool.database.table

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
* Created by Shudipto Trafder on 11/15/2017.
*/

@Entity
data class Author(@PrimaryKey val authorID:Int,
                  val authorName:String,
                  val authorLink: String,
                  val authorDescription: String,
                  val authorAvatarUrls: String)