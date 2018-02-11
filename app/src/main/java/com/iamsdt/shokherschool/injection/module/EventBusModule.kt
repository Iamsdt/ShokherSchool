package com.iamsdt.shokherschool.injection.module

import com.iamsdt.shokherschool.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus

/**
 * Created by Shudipto Trafder on 2/11/2018.
 * at 11:00 AM
 */

@Module
class EventBusModule{
    @Provides
    @ApplicationScope
    fun getBus():EventBus = EventBus.builder()
            .logNoSubscriberMessages(false)
            .sendNoSubscriberEvent(false).installDefaultEventBus()
}