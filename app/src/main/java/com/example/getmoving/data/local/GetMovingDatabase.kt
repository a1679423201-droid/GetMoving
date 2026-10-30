package com.example.getmoving.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeightEntryEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class GetMovingDatabase : RoomDatabase() {
    abstract fun weightEntryDao(): WeightEntryDao
}
