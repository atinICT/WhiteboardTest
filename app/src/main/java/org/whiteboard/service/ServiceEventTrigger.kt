package org.whiteboard.service

import android.app.IntentService
import android.content.Intent
import dagger.android.AndroidInjection
import org.whiteboard.di.modules.data.DataManager
import javax.inject.Inject

class ServiceEventTrigger : IntentService("event") {

    @Inject
    lateinit var mDataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onHandleIntent(p0: Intent?) {
    }

}