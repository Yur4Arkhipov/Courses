package com.example.courses.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "settings")

suspend fun setOnboardingCompleted(context: Context) {
    context.dataStore.edit { prefs ->
        prefs[booleanPreferencesKey("onboarding_completed")] = true
    }
}

suspend fun isOnboardingCompleted(context: Context): Boolean {
    val prefs = context.dataStore.data.first()
    return prefs[booleanPreferencesKey("onboarding_completed")] == true
}
