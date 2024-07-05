package com.swm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swm.data.network.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
) : ViewModel() {
    private val id = MutableStateFlow(1) // for test
    private val _userDto = MutableStateFlow(UserDto())
    val userDto = _userDto.asStateFlow()

    fun callApi() = viewModelScope.launch {
        userDataSource.getUser(id.value.toString())
            .onSuccess { _userDto.value = it.userDto }
            .onFailure { _userDto.value = UserDto(firstName = "ERROR!") }

        id.value = id.value.plus(1)
    }
}