package org.whiteboard.di.modules.data

import android.content.Context
import org.whiteboard.di.data.event.EventManager
import org.whiteboard.di.modules.data.pref.PreferencesManager
import org.whiteboard.di.modules.data.sourceChannel.SourceChannelManager


interface DataManager {

    val context: Context


    val preferencesManager: PreferencesManager

    val sourceChannelManager: SourceChannelManager

    val eventManager: EventManager

}

