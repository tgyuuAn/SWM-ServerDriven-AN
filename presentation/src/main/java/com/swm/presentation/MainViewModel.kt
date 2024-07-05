package com.swm.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swm.domain.usecase.GetScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getScreenUseCase: GetScreenUseCase,
) : ViewModel() {
    fun getScreen() = viewModelScope.launch {
        getScreenUseCase()
            .onSuccess { Log.d("test", it.toString()) }
            .onFailure { Log.d("test", it.toString()) }
    }
}