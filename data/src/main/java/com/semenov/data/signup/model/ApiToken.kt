package com.semenov.data.signup.model

import com.google.gson.annotations.SerializedName
import com.semenov.domain.signuprepository.model.Token

data class ApiToken(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String
)

fun ApiToken.mapToDomain() = Token(
    success = success,
    token = token
)
