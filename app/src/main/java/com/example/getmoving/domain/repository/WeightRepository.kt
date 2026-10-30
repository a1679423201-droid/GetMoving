package com.example.getmoving.domain.repository

import com.example.getmoving.domain.model.WeightEntry
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface WeightRepository {
    fun observeRecent(days: Int): Flow<List<WeightEntry>>
    fun observe(date: LocalDate): Flow<WeightEntry?>
    suspend fun upsert(date: LocalDate, weightTenths: Int, isBackfill: Boolean)
    suspend fun delete(date: LocalDate)
}
