package com.swm.data.network.repository

import com.swm.data.network.source.ScreenDataSource
import com.swm.domain.model.Screen
import com.swm.domain.repository.ScreenRepository
import javax.inject.Inject

class ScreenRepositoryImpl @Inject constructor(
    private val screenDataSource: ScreenDataSource,
) : ScreenRepository {
    override suspend fun getScreen(): Result<Screen> = screenDataSource.getScreen()
}