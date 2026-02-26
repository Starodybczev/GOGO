package com.example.prac_five.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


val Context.dataStore by preferencesDataStore(name = "reader_prefs")

object ReaderPref {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
}


suspend fun saveDarkTheme(context: Context, value: Boolean) {
    context.dataStore.edit { prefs ->
        prefs[ReaderPref.DARK_THEME] = value
    }
}

suspend fun loadDarkTheme(context: Context): Boolean {
    val prefs = context.dataStore.data.first()
    return prefs[ReaderPref.DARK_THEME] ?: false
}