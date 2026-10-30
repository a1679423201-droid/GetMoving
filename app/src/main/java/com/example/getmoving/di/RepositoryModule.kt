package com.example.getmoving.di

import com.example.getmoving.data.repository.DefaultSettingsRepository
import com.example.getmoving.data.repository.DefaultWeightRepository
import com.example.getmoving.domain.repository.SettingsRepository
import com.example.getmoving.domain.repository.WeightRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeightRepository(impl: DefaultWeightRepository): WeightRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: DefaultSettingsRepository): SettingsRepository
}
