package com.example.getmoving.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES_NAME = "user_preferences"

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME,
)

object PreferenceKeys {
    val TargetWeightTenths = intPreferencesKey("target_weight_tenths")
}
