package com.swm.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swm.domain.model.RichTextVO
import com.swm.domain.model.Screen
import com.swm.domain.repository.RichTextScreenRepository
import com.swm.domain.repository.ScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val screenRepository: ScreenRepository,
    private val richTextScreenRepository: RichTextScreenRepository,
) : ViewModel() {
    private val _screen = MutableStateFlow<Screen>(Screen())
    val screen = _screen.asStateFlow()

    private val _richTextScreen = MutableStateFlow<RichTextVO>(RichTextVO())
    val richTextScreen = _richTextScreen.asStateFlow()

    init {
        getScreen()
        getRichTextScreen()
    }

    private fun getScreen() = viewModelScope.launch {
        screenRepository
            .getScreen()
            .onSuccess { _screen.value = it }
            .onFailure { Log.d("test", it.toString()) }
    }

    // ✅ Rich Text
    private fun getRichTextScreen() = viewModelScope.launch {
        richTextScreenRepository
            .getRichTextScreen()
            .onSuccess {
                _richTextScreen.value = it
                Log.d("test", "ViewModel ${it}")
            }
            .onFailure { Log.d("RichText test fail", it.toString()) }
    }
}