package com.swm.domain.usecase

import com.swm.domain.repository.ScreenRepository
import javax.inject.Inject

class GetScreenUseCase @Inject constructor(
    private val screenRepository: ScreenRepository,
) {
    suspend operator fun invoke() = screenRepository.getScreen()
}