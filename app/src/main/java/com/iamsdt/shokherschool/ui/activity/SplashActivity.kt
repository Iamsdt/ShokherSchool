package com.iamsdt.shokherschool.ui.activity

import android.content.Intent
import android.os.Bundle
import com.iamsdt.shokherschool.BuildConfig
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.utilities.SpUtils
import com.iamsdt.shokherschool.data.utilities.Utility
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.services.DataInsertService
import com.iamsdt.shokherschool.ui.services.PostDataService
import com.iamsdt.shokherschool.ui.services.UpdateServices
import timber.log.Timber

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //set theme
        //ThemeUtils.initialize(this)
        setContentView(R.layout.activity_splash)
        //setSupportActionBar(splashToolbar)

        var timer: Long = 1000 //1s
        if (BuildConfig.DEBUG) {
            timer = 100
        }

        //if service is not completed
        //start the services
        if (!SpUtils.isServiceComplete(this)) {
            startService(Intent(this, DataInsertService::class.java))
            if (!SpUtils.isPostServiceComplete(this)){
                startService(Intent(this, PostDataService::class.java))
            }
        }

        if (SpUtils.isAppRunFirstTime(this)) {
            //start service and app intro
            //complete 2/10/2018 start app intro
            Thread({
                try {
                    Thread.sleep(timer)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e(e, "Error on Splash Activity Thread")
                } finally {
                    startActivity(Intent(this@SplashActivity,
                            MyAppIntro::class.java))
                    finish()

                    //same
                    SpUtils.saveAppRunFirstTime(this)
                }
            }).start()

        } else {
            Thread({
                try {
                    Thread.sleep(timer)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e(e, "Error on Splash Activity Thread")
                } finally {
                    startActivity(Intent(this@SplashActivity,
                            MainActivity::class.java))
                    finish()
                }
            }).start()
        }
    }

    override fun onStart() {
        super.onStart()
        //check first that this day is older than 7 days
        //because, want to run service on 7 days interval

        //complete 1/10/2018 add user choice option to sync date interval

        //complete 2/10/2018 run update service
        if (Utility.isTimeForRunService(this@SplashActivity)) {
            val intent = Intent(this@SplashActivity, UpdateServices::class.java)
            startService(intent)
            //save date
            SpUtils.saveUpdateServiceDateOnSp(this@SplashActivity)
        }
    }
}
