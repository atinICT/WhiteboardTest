package org.whiteboard.di.modules.data.pref

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.whiteboard.R
import javax.inject.Singleton

@Module
class SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.pref_name),
            Context.MODE_PRIVATE
        )

    }

    @Provides
    @Singleton
    fun providePreferencesManager(
        context: Context,
        sharedPreferences: SharedPreferences
    ): PreferencesManager {
        return PreferencesManager(context, sharedPreferences)
    }

}
