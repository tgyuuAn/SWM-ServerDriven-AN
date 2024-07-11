package com.swm.data.network.source

import com.swm.data.network.di.ServerDrivenApi
import com.swm.data.network.util.await
import com.swm.domain.model.Screen
import javax.inject.Inject

class ScreenDataSource @Inject constructor(
    private val api: ServerDrivenApi,
) {
    suspend fun getScreen(): Result<Screen> = api.getScreen()
        .await()
        .mapCatching { it }
}