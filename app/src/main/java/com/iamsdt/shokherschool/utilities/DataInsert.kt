package com.iamsdt.shokherschool.utilities

import android.content.Context
import com.iamsdt.shokherschool.database.DataSaver

/**
 * Created by Shudipto Trafder on 11/28/2017.
 * at 12:30 AM
 */
class DataInsert{
    companion object {
        fun dataInsertStart(context: Context){
            Thread(Runnable {
                DataSaver(context)
            }).start()
        }
    }
}