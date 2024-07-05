package com.swm.data.network.model

import com.google.gson.annotations.SerializedName

data class ReqresResponse(
    @SerializedName("data")
    val userDto : UserDto
)

data class UserDto(
    val avatar: String = "",
    val email: String = "",
    @SerializedName("first_name")
    val firstName: String = "",
    val id: Int = 1,
    @SerializedName("last_name")
    val lastName: String = "",
)
