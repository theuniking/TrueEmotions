package com.verome.core.data.local.preferences

interface PreferenceManager {
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    fun putLong(key: String, value: Long)
    fun getLong(key: String): Long
    fun clear()
}