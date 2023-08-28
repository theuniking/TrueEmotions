package com.verome.core.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.verome.core.data.local.preferences.PreferenceConstants.KEY_PREFERENCE_NAME

class DefaultPreferenceManager(context: Context) : PreferenceManager {
    companion object {
        private lateinit var sharedPreferences: SharedPreferences
    }

    init {
        sharedPreferences = context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    override fun putString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String): String? = sharedPreferences.getString(key, null)

    override fun putLong(key: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    override fun getLong(key: String): Long = sharedPreferences.getLong(key, 0L)

    override fun clear() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}