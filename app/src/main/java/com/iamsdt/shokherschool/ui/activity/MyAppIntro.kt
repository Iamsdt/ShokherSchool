package com.iamsdt.shokherschool.ui.activity

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.MessageButtonBehaviour
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.utilities.SpUtils

class MyAppIntro : MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.mipmap.ic_launcher)
                .title("title 2")
                .description("Description 3")
                .build())

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                .title("title 3")
                .description("Description 3")
                .build())


        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                .title("title 4")
                .description("Description 3")
                .build())

        addSlide(SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                .title("title 5")
                .description("Description 3")
                .build(), MessageButtonBehaviour(View.OnClickListener {
            nextActivity()
        }, "Start"))
    }

    override fun onFinish() {
        super.onFinish()
        nextActivity()
    }

    private fun nextActivity() {
        startActivity(Intent(this,
                MainActivity::class.java))
        finish()
        SpUtils.saveAppRunFirstTime(this)
    }
}
