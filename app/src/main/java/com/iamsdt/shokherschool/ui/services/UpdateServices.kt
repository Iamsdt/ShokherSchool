package com.iamsdt.shokherschool.ui.services

import android.content.Intent
import android.os.IBinder
import com.iamsdt.shokherschool.ui.base.BaseServices

/**
 * Created by Shudipto Trafder on 2/11/2018.
 * at 10:08 AM
 */
class UpdateServices:BaseServices(){
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        this.stopSelf()
    }

}