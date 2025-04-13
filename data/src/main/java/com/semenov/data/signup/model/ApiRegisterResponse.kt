package com.semenov.data.signup.model

import com.google.gson.annotations.SerializedName
import com.semenov.domain.signuprepository.model.RegisterResponse

data class ApiRegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("message") val message: String
)

fun ApiRegisterResponse.mapToDomain() = RegisterResponse(
    success = success,
    userId = userId,
    message = message
)