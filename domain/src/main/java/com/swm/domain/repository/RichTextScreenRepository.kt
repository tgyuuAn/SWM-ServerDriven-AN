package com.swm.domain.repository

import com.swm.domain.model.RichTextVO

interface RichTextScreenRepository {
    suspend fun getRichTextScreen(): Result<RichTextVO>
}