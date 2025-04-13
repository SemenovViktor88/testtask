package com.semenov.data.signup.model

import com.google.gson.annotations.SerializedName
import com.semenov.domain.signuprepository.model.RegisterUser

data class ApiRegisterUser(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("position_id") val positionId: Int,
    @SerializedName("photo") val photo: String,
)

fun RegisterUser.mapToApi() = ApiRegisterUser(
    name = name,
    email = email,
    phone = phone,
    positionId = positionId,
    photo = photo
)