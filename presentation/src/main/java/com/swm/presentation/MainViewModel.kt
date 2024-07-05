package com.swm.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swm.domain.repository.ScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val screenRepository: ScreenRepository,
) : ViewModel() {
    fun getScreen() = viewModelScope.launch {
        screenRepository.getScreen()
            .onSuccess { Log.d("test", it.toString()) }
            .onFailure { Log.d("test", it.toString()) }
    }
}