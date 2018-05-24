package com.iamsdt.shokherschool.injection

import com.iamsdt.shokherschool.injection.module.ActivityModule
import com.iamsdt.shokherschool.injection.scopes.ActivityScope
import com.iamsdt.shokherschool.ui.activity.*
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/29/2017.
 * at 10:52 PM
 */

@ActivityScope
@Component(modules = [ActivityModule::class],
        dependencies = [MyApplicationComponent::class])
interface ActivityComponent{

    fun inject(mainActivity: MainActivity)
    fun inject(settingsActivity: SettingsActivity)
    fun inject(detailsActivity: DetailsActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(bookmarkActivity: BookmarkActivity)
}