package org.whiteboard.di.data.event

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.whiteboard.BuildConfig
import org.whiteboard.di.modules.data.sourceChannel.SourceChannelManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventManager @Inject constructor(
    private val context: Context,
    private val sourceChannelManager: SourceChannelManager
) {

    fun onNewFCMToken(token: String) {
    }

//    fun setEventBatch( status: String) {
//        val name = sourceChannelManager?.getSimpleSource()
//        val data = BatchEventData()
//        data.put("SourceChannel", name)
//        Batch.User.trackEvent(name + status, name, data)
//    }

    fun setEvent(name: String, bundle: Bundle = Bundle()) {
        FirebaseAnalytics.getInstance(context).apply {
//            setUserProperty(
//                "userMode",
//                accountManager.getUserData(context.getString(R.string.userMode)) ?: "NO_USER"
//            )
            setUserProperty("sourceChannel", sourceChannelManager.getRawSource())
            setUserProperty("version", BuildConfig.VERSION_NAME)
            logEvent("${eventPrefix()}$name", bundle)
        }
    }

    private fun eventPrefix(): String {
        return when {
            sourceChannelManager.isGooglePlay -> "g_"
            sourceChannelManager.isCafeBazaar -> "c_"
            sourceChannelManager.isMyket -> "m_"
            else -> "o_"
        }
    }

    companion object {

        fun setEvent(context: Context, name: String, bundle: Bundle = Bundle()) {
            FirebaseAnalytics.getInstance(context).logEvent(name, bundle)
        }
    }

}