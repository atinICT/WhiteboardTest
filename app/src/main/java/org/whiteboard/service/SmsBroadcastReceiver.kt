package org.whiteboard.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Pattern

class SmsBroadcastReceiver : BroadcastReceiver() {

    private var listener: Listener? = null

    fun injectListener(listener: Listener?) {
        this.listener = listener
    }


    override fun onReceive(context: Context?, intent: Intent?) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val bundle = intent.extras
            val status = bundle?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val msg = bundle.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                    val pattern = Pattern.compile("\\d{4}")
                    val matcher = pattern.matcher(msg)
                    if (matcher.find()) {
                        this.listener?.onSMSReceived(matcher.group(0))
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    this.listener?.onTimeOut()
                }
            }
        }
    }

    interface Listener {
        fun onSMSReceived(otp: String)

        fun onTimeOut()
    }
}