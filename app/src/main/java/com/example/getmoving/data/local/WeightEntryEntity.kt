package com.example.getmoving.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries")
data class WeightEntryEntity(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "weight_tenths") val weightTenths: Int,
    @ColumnInfo(name = "is_backfill") val isBackfill: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
