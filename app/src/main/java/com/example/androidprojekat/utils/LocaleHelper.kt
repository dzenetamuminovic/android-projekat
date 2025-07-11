package com.example.androidprojekat.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.*

object LocaleHelper {

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun saveLanguagePreference(context: Context, langCode: String) {
        val prefs = context.getSharedPreferences("language_pref", Context.MODE_PRIVATE)
        prefs.edit().putString("lang", langCode).apply()
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences("language_pref", Context.MODE_PRIVATE)
        return prefs.getString("lang", "bs") ?: "bs"
    }
}