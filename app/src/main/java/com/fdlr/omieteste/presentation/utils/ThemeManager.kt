package com.fdlr.omieteste.presentation.utils

import android.content.Context
import com.fdlr.omieteste.presentation.utils.Constants.DARK_THEME
import com.fdlr.omieteste.presentation.utils.Constants.OMIE_PREFERENCES

fun saveThemePreference(context: Context, isDarkTheme: Boolean) {
    val sharedPrefs = context.getSharedPreferences(OMIE_PREFERENCES, Context.MODE_PRIVATE)
    sharedPrefs.edit().putBoolean(DARK_THEME, isDarkTheme).apply()
}

fun loadThemePreference(context: Context): Boolean {
    val sharedPrefs = context.getSharedPreferences(OMIE_PREFERENCES, Context.MODE_PRIVATE)
    return sharedPrefs.getBoolean(DARK_THEME, true)
}