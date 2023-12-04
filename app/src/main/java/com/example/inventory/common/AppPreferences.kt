package com.example.inventory.common

import android.content.SharedPreferences
import com.google.gson.Gson

class AppPreferences(private val preferences: SharedPreferences, private val gson: Gson) {

    companion object {
        var PREF_LOGOUT_HEADER_INCLUDE = "pref.logout.header.include"
    }

    var shouldIncludeLogoutHeader: Boolean?
    get() = preferences.getBoolean(PREF_LOGOUT_HEADER_INCLUDE, false)
    set(boolean) {
        if (boolean != null) {
            preferences.edit().putBoolean(PREF_LOGOUT_HEADER_INCLUDE, boolean).apply()
        }
    }


}