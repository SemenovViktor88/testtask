package com.semenov.domain.signuprepository.model

data class RegisterResponse(
    val success: Boolean,
    val userId: Int,
    val message: String
)