package com.swm.data.network.source

import com.swm.data.network.di.ServerDrivenRichTextApi
import com.swm.data.network.util.await
import com.swm.domain.model.RichTextVO
import javax.inject.Inject

class RichTextScreenDataSource @Inject constructor(
    private val api: ServerDrivenRichTextApi
) {
    suspend fun getRichTextScreen(): Result<RichTextVO> = api.getRichTextScreen()
        .await()
        .mapCatching { it }
}