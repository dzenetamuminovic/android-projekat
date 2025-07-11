package com.example.androidprojekat.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

object LanguageDataStore {

    private val LANGUAGE_KEY = stringPreferencesKey("language")

    fun getLanguage(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: "bs"
        }
    }

    suspend fun setLanguage(context: Context, langCode: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = langCode
        }
    }
}
