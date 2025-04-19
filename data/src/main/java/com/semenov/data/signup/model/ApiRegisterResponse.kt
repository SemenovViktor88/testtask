package com.semenov.data.signup.model

import com.google.gson.annotations.SerializedName

data class ApiRegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("message") val message: String
)