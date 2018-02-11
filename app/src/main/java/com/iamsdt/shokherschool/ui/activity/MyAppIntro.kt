package com.iamsdt.shokherschool.ui.activity

import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.iamsdt.shokherschool.R


class MyAppIntro : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        isFullscreen = true
        super.onCreate(savedInstanceState)

        isButtonBackVisible = false
        isButtonNextVisible = false
        isButtonCtaVisible = true
        buttonCtaTintMode = BUTTON_CTA_TINT_MODE_TEXT

        //todo 2/11/2018 complete app intro

        addSlide(SimpleSlide.Builder()
                .title("First app")
                .description("Hello word")
                .image(R.drawable.ic_menu_slideshow)
                .background(R.color.amber_500)
                .backgroundDark(R.color.amber_900)
                .build())

        addSlide(SimpleSlide.Builder()
                .title("First app 2")
                .description("Hello word 2")
                .image(R.drawable.ic_menu_send)
                .background(R.color.blue_500)
                .backgroundDark(R.color.blue_900)
                .build())
    }
}
