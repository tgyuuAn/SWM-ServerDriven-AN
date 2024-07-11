package com.swm.data.network.repository

import com.swm.data.network.source.RichTextScreenDataSource
import com.swm.domain.model.RichTextVO
import com.swm.domain.repository.RichTextScreenRepository
import javax.inject.Inject

class RichTextScreenRepositoryImpl @Inject constructor(
    private val richTextScreenDataSource: RichTextScreenDataSource,
) : RichTextScreenRepository {
    override suspend fun getRichTextScreen(): Result<RichTextVO> = richTextScreenDataSource.getRichTextScreen()
}