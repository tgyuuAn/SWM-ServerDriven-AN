package com.swm.data.network.util

import retrofit2.Response

internal fun <T> Response<T>.await(): Result<T> {
    if (!isSuccessful) {
        return Result.failure(errorBody()?.string()?.let { Throwable(it) }
            ?: Throwable("Response Error"))
    }

    if (body() == null) {
        return Result.failure(Throwable("Response Body is Null"))
    }

    return Result.success(body()!!)
}