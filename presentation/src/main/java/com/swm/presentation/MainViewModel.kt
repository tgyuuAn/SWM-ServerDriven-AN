package com.swm.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swm.domain.model.Screen
import com.swm.domain.usecase.GetScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getScreenUseCase: GetScreenUseCase,
) : ViewModel() {
    private val _screen = MutableStateFlow<Screen>(Screen())
    val screen = _screen.asStateFlow()

    fun getScreen() = viewModelScope.launch {
        getScreenUseCase()
            .onSuccess {
                _screen.value = it
            }
            .onFailure { Log.d("test", it.toString()) }
    }
}