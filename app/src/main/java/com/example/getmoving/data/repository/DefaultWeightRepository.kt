package com.example.getmoving.data.repository

import com.example.getmoving.data.local.WeightEntryDao
import com.example.getmoving.data.local.WeightEntryEntity
import com.example.getmoving.domain.model.WeightEntry
import com.example.getmoving.domain.repository.WeightRepository
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DefaultWeightRepository @Inject constructor(
    private val dao: WeightEntryDao,
    private val clock: Clock,
) : WeightRepository {

    override fun observeRecent(days: Int): Flow<List<WeightEntry>> =
        dao.observeRecent(days).map { entities -> entities.map(WeightEntryEntity::toDomain) }

    override fun observe(date: LocalDate): Flow<WeightEntry?> =
        dao.observeByDate(date.toString()).map { entity -> entity?.toDomain() }

    override suspend fun upsert(date: LocalDate, weightTenths: Int, isBackfill: Boolean) {
        dao.upsert(
            date = date.toString(),
            weightTenths = weightTenths,
            isBackfill = isBackfill,
            now = clock.millis(),
        )
    }

    override suspend fun delete(date: LocalDate) {
        dao.deleteByDate(date.toString())
    }
}

private fun WeightEntryEntity.toDomain(): WeightEntry = WeightEntry(
    date = LocalDate.parse(date),
    weightTenths = weightTenths,
    isBackfill = isBackfill,
)
