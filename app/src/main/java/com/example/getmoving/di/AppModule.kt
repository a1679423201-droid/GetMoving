package com.example.getmoving.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.getmoving.data.local.GetMovingDatabase
import com.example.getmoving.data.local.WeightEntryDao
import com.example.getmoving.data.prefs.userPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClock(): Clock = Clock.systemDefaultZone()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GetMovingDatabase =
        Room.databaseBuilder(
            context,
            GetMovingDatabase::class.java,
            "getmoving.db",
        ).build()

    @Provides
    fun provideWeightEntryDao(database: GetMovingDatabase): WeightEntryDao =
        database.weightEntryDao()

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.userPreferencesDataStore
}
