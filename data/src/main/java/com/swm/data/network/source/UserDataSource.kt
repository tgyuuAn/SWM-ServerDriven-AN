package com.swm.data.network.source

import com.swm.data.network.di.ServerDrivenApi
import com.swm.data.network.model.ReqresResponse
import com.swm.data.network.util.await
import javax.inject.Inject

// For Test
class UserDataSource @Inject constructor(
    private val serverDrivenApi: ServerDrivenApi,
) {
    suspend fun getUser(id: String): Result<ReqresResponse> = serverDrivenApi.getUser(id).await()
}