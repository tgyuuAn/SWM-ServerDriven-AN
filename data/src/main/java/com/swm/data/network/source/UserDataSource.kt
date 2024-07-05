package com.swm.data.network.source

import com.swm.data.network.di.ServerDrivenApi
import javax.inject.Inject

// For Test
class UserDataSource @Inject constructor(
    private val serverDrivenApi: ServerDrivenApi,
) {
    private suspend fun getUser(id: String) = serverDrivenApi.getUser(id)
}