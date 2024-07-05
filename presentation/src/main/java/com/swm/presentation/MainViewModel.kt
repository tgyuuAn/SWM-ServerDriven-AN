package com.swm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swm.data.network.model.UserDto
import com.swm.data.network.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
) : ViewModel() {
    private val id = MutableStateFlow(0) // for test
    val userDto = MutableStateFlow(UserDto())

    fun callApi() = viewModelScope.launch {
        userDataSource.getUser(id.value.toString())
            .onSuccess { userDto.value = it }
            .onFailure { userDto.value = UserDto(firstName = "ERROR!") }

        id.value = id.value.plus(1)
    }
}