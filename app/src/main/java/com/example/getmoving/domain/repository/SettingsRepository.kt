package com.example.getmoving.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeTargetWeightTenths(): Flow<Int?>
    suspend fun setTargetWeightTenths(value: Int)
    suspend fun clearTargetWeight()
}
