package org.whiteboard.di.modules.data

import android.content.Context
import org.whiteboard.di.data.event.EventManager
import org.whiteboard.di.modules.data.pref.PreferencesManager
import org.whiteboard.di.modules.data.sourceChannel.SourceChannelManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager
@Inject
constructor(
    override val context: Context,
    override val preferencesManager: PreferencesManager,
    override val sourceChannelManager: SourceChannelManager,
    override val eventManager: EventManager
) : DataManager


