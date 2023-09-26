package com.example.chall3.utils

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun saveLayoutPreferences(isListView: Boolean) {
        prefs.edit().putBoolean(LAYOUT_KEY, isListView).apply()
    }

    fun getLayoutPreferences(): Boolean {
        return prefs.getBoolean(LAYOUT_KEY, true)
    }

    companion object {
        private const val NAME = "UserPrefs"
        private const val LAYOUT_KEY = "LayoutKey"
    }
}