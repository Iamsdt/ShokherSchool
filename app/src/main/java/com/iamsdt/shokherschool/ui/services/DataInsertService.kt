package com.iamsdt.shokherschool.ui.services

import android.annotation.SuppressLint
import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.ui.base.BaseServices

@SuppressLint("Registered")
/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:10 AM
 */
class DataInsertService:BaseServices(){

    override fun onCreate() {
        getComponent().inject(this)
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}