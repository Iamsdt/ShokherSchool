package com.iamsdt.shokherschool.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.iamsdt.shokherschool.BuildConfig
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.utilities.SpUtils
import com.iamsdt.shokherschool.data.utilities.ThemeUtils
import com.iamsdt.shokherschool.data.utilities.Utility
import com.iamsdt.shokherschool.ui.base.BaseActivity
import com.iamsdt.shokherschool.ui.services.DataInsertService
import com.iamsdt.shokherschool.ui.services.PostDataService
import com.iamsdt.shokherschool.ui.services.UpdateServices
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber

class SplashActivity : BaseActivity() {

    private val requestCodeIntro = 10

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //set theme
        ThemeUtils.initialize(this)
        setContentView(R.layout.activity_splash)
        setSupportActionBar(toolbar)

        var timer: Long = 1000 //1s
        if (BuildConfig.DEBUG) {
            timer = 100
        }

        //if service is not completed
        //start the services
        if (!SpUtils.isServiceComplete(this)) {
            if (!SpUtils.isPostServiceComplete(this)){
                startService(Intent(this, PostDataService::class.java))
            } else{
                startService(Intent(this, DataInsertService::class.java))
            }
        }

        if (SpUtils.isAppRunFirstTime(this)) {
            //start service and app intro
            //complete 2/10/2018 start app intro
            Thread({
                try {
                    // time 1 sec
                    Thread.sleep(5000)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Timber.e(e, "Error on Splash Activity Thread")
                } finally {
//                    startActivityForResult(Intent(this@SplashActivity,
//                            MyAppIntro::class.java),requestCodeIntro)

                    startActivity(Intent(this@SplashActivity,
                            MainActivity::class.java))
                    finish()

                    //same
                    SpUtils.saveAppRunFirstTime(this)
                }
            }).start()

        } else {
            Thread({
                try {
                    //complete 1/18/2018 change time
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

        //complete 2/10/2018 run update service
        if (Utility.isTimeForRunService(this@SplashActivity)) {
            val intent = Intent(this@SplashActivity, UpdateServices::class.java)
            startService(intent)
            //save date
            SpUtils.saveUpdateServiceDateOnSp(this@SplashActivity)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeIntro) {
            if (resultCode == RESULT_OK) {
                SpUtils.saveAppRunFirstTime(this)
            } else{
                SpUtils.saveAppRunFirstTime(this)
            }

            startActivity(Intent(this@SplashActivity,
                    MainActivity::class.java))
            finish()
        }
    }
}
