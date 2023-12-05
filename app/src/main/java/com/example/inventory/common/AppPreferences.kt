package com.example.inventory.common

import android.content.SharedPreferences
import com.google.gson.Gson

class AppPreferences(private val preferences: SharedPreferences, private val gson: Gson) {

    companion object {
        var PREF_LOGOUT_HEADER_INCLUDE = "pref.logout.header.include"
        var PREF_SESSION_ID = "pref.session.id"
        var PREF_SESSION_NAME = "pref.session.name"
        var PREF_SESSION_TOKEN = "pref.session.token"
        var PREF_USER_NAME = "pref.user.name"
    }

    var shouldIncludeLogoutHeader: Boolean?
    get() = preferences.getBoolean(PREF_LOGOUT_HEADER_INCLUDE, false)
    set(boolean) {
        if (boolean != null) {
            preferences.edit().putBoolean(PREF_LOGOUT_HEADER_INCLUDE, boolean).apply()
        }
    }

    var sessionId: String?
        get() = preferences.getString(PREF_SESSION_ID, "")
        set(type) {
            preferences.edit().putString(PREF_SESSION_ID, type).apply()
        }

    var sessionName: String?
        get() = preferences.getString(PREF_SESSION_NAME, "")
        set(type) {
            preferences.edit().putString(PREF_SESSION_NAME, type).apply()
        }

    var sessionToken: String?
        get() = preferences.getString(PREF_SESSION_TOKEN, "")
        set(type) {
            preferences.edit().putString(PREF_SESSION_TOKEN, type).apply()
        }

    var userName: String?
        get() = preferences.getString(PREF_USER_NAME, "")
        set(type) {
            preferences.edit().putString(PREF_USER_NAME, type).apply()
        }


}