package org.whiteboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import org.whiteboard.di.components.DaggerAppComponent
import timber.log.Timber


class App : DaggerApplication(), LifecycleObserver {

    @Volatile
    var isAppForeground = false


    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initFirebase()

        initFabric()

        initTimber()

        initStetho()

        initCharkhoneSdkApp()

        RxJavaPlugins.setErrorHandler(Timber::e)
    }

    private fun initFabric() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        isAppForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        isAppForeground = true
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initCharkhoneSdkApp() {

    }

    private fun initTimber() {
        Timber.plant(
                Timber.DebugTree()
        )
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }



    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().application(this.applicationContext).build()
        component.inject(this)
        return component
    }

}
