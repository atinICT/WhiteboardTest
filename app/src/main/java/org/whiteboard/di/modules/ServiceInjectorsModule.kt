package org.whiteboard.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.whiteboard.service.ServiceEventTrigger
import org.whiteboard.service.ServiceFirebaseMessage


@Module
abstract class ServiceInjectorsModule {


    @ContributesAndroidInjector
    abstract fun serviceEventTriggerInjector(): ServiceEventTrigger

    @ContributesAndroidInjector
    abstract fun serviceFirebaseMessageInjector(): ServiceFirebaseMessage

}