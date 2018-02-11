package com.iamsdt.shokherschool.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.utilities.SpUtils
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.data.utilities.Utility
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.services.DataInsertService
import com.iamsdt.shokherschool.ui.services.PostDataService
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_splash)
        setSupportActionBar(toolbar)

        if (SpUtils.isAppRunFirstTime(this)) {
            //start service and app intro
            startService(Intent(this,PostDataService::class.java))
            //todo 2/10/2018 start app intro
            Thread({
                try {
                    //fixme 1/18/2018 change time
                    Thread.sleep(5000)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e(e, "Error on Splash Activity Thread")
                } finally {
                    startActivity(Intent(this@SplashActivity,
                            MainActivity::class.java))
                    finish()
                }
            }).start()

        } else{
            Thread({
                try {
                    //fixme 1/18/2018 change time
                    Thread.sleep(100)

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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        //check first that this day is older than 7 days
        //because, want to run service on 7 days interval

        //complete 1/10/2018 add user choice option to sync date interval

        //todo 2/10/2018 run update service
        if (Utility.isTimeForRunService(this@SplashActivity)) {
            if (true) {
                val intent = Intent(this@SplashActivity, DataInsertService::class.java)
                startService(intent)
                //save date
                SpUtils.saveUpdateServiceDateOnSp(this@SplashActivity)
            }
        }
    }

}
