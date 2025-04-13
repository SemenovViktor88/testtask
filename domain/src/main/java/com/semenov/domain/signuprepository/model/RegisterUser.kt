package com.semenov.domain.signuprepository.model

data class RegisterUser(
    val name: String,
    val email: String,
    val phone: String,
    val positionId: Int,
    val photo: String,
)