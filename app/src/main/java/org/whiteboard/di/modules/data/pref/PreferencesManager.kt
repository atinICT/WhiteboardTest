package org.whiteboard.di.modules.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes


class PreferencesManager constructor(var context: Context, private var mPrefs: SharedPreferences) {

    private var editor = mPrefs.edit()

    fun clearPrefs() {
        editor.clear()
        editor.apply()
    }

    fun putString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun putString(@StringRes key: Int, @StringRes value: Int) {
        putString(context.getString(key), context.getString(value))
    }

    fun putString(@StringRes key: Int, value: String) {
        putString(context.getString(key), value)
    }

    fun putBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun putBoolean(@StringRes key: Int, value: Boolean) {
        putBoolean(context.getString(key), value)
    }

    fun putInt(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun putInt(@StringRes key: Int, value: Int) {
        putInt(context.getString(key), value)
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return try {
            mPrefs.getString(key, defaultValue)
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    fun getString(@StringRes key: Int, @StringRes defaultValue: Int): String? {
        return getString(context.getString(key), context.getString(defaultValue))
    }

    fun getString(@StringRes key: Int, defaultValue: String): String? {
        return getString(context.getString(key), defaultValue)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return try {
            mPrefs.getBoolean(key, defaultValue)
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    fun getBoolean(@StringRes key: Int, defaultValue: Boolean): Boolean {
        return getBoolean(context.getString(key), defaultValue)
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return try {
            mPrefs.getInt(key, defaultValue)
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    fun getInt(@StringRes key: Int, defaultValue: Int): Int {
        return getInt(context.getString(key), defaultValue)
    }

}

