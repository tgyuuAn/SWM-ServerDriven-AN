package com.swm.domain.repository

import com.swm.domain.model.Screen

interface ScreenRepository {
    suspend fun getScreen(): Result<Screen>
}