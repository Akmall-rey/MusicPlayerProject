package com.example.musicplayerproject.ui.profile

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val NAME = "name"
        private const val EMAIL = "email"
    }
    private val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    fun setUser(value: User) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.apply()
    }
    fun getUser(): User {
        val model = User()
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        return model
    }
}