package com.swm.data.network.di

import com.swm.data.network.repository.ScreenRepositoryImpl
import com.swm.data.network.source.ScreenDataSource
import com.swm.domain.repository.ScreenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideScreenRepository(screenDataSource: ScreenDataSource): ScreenRepository =
        ScreenRepositoryImpl(screenDataSource)
}