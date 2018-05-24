package com.iamsdt.shokherschool.injection

import com.iamsdt.shokherschool.injection.module.FragmentModule
import com.iamsdt.shokherschool.injection.scopes.FragmentScope
import com.iamsdt.shokherschool.ui.fragment.FakeFragment
import dagger.Component

/**
 * Created by Shudipto Trafder on 1/7/2018.
 * at 12:32 AM
 */
@FragmentScope
@Component(modules = [FragmentModule::class],
        dependencies = [MyApplicationComponent::class])
interface FragmentComponent {
    fun inject(fakeFragment: FakeFragment)
}