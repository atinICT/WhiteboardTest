package org.whiteboard.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.whiteboard.MainActivity
import org.whiteboard.di.MainActivityModule

@Module
abstract class ActivityInjectorsModule {


    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity



}