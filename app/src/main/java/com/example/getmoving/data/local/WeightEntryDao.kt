package com.example.getmoving.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightEntryDao {

    @Query("SELECT * FROM weight_entries ORDER BY date DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<WeightEntryEntity>>

    @Query("SELECT * FROM weight_entries WHERE date = :date LIMIT 1")
    fun observeByDate(date: String): Flow<WeightEntryEntity?>

    @Query(
        """
        INSERT INTO weight_entries (date, weight_tenths, is_backfill, created_at, updated_at)
        VALUES (:date, :weightTenths, :isBackfill, :now, :now)
        ON CONFLICT(date) DO UPDATE SET
            weight_tenths = excluded.weight_tenths,
            updated_at = excluded.updated_at
        """,
    )
    suspend fun upsert(
        date: String,
        weightTenths: Int,
        isBackfill: Boolean,
        now: Long,
    )

    @Query("DELETE FROM weight_entries WHERE date = :date")
    suspend fun deleteByDate(date: String)
}
