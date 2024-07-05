package com.swm.data.network.source

import com.swm.data.network.di.ServerDrivenApi
import com.swm.data.network.model.ScreenDTO
import com.swm.data.network.util.await
import javax.inject.Inject

class ScreenDataSource @Inject constructor(
    private val api: ServerDrivenApi,
) {
    suspend fun getScreen(screen: String): Result<ScreenDTO> = api.getScreen(screen).await()
}