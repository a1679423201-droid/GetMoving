package com.example.getmoving.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.getmoving.data.prefs.PreferenceKeys
import com.example.getmoving.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DefaultSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    override fun observeTargetWeightTenths(): Flow<Int?> =
        dataStore.data.map { preferences -> preferences[PreferenceKeys.TargetWeightTenths] }

    override suspend fun setTargetWeightTenths(value: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.TargetWeightTenths] = value
        }
    }

    override suspend fun clearTargetWeight() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.TargetWeightTenths)
        }
    }
}
