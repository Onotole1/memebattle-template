package ru.memebattle.core.utils

import android.content.SharedPreferences

fun SharedPreferences.putString(key: String, value: String) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.getString(key: String) =
    getString(key, null)