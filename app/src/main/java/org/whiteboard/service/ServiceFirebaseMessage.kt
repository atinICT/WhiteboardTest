package org.whiteboard.service

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import org.whiteboard.di.modules.data.DataManager
import timber.log.Timber
import javax.inject.Inject

class ServiceFirebaseMessage : FirebaseMessagingService() {

    @Inject
    lateinit var mDataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    @SuppressLint("CheckResult")
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.d(p0)
        mDataManager.eventManager.onNewFCMToken(p0)

    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("From: ${remoteMessage.from}")
        Timber.d("Message Notification Body: ${remoteMessage.notification?.title}")
        Timber.d("Message Notification Body: ${remoteMessage.notification?.body}")
        Timber.d("Message data payload: ${remoteMessage.data}")
        // Check if message contains a data payload.

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
