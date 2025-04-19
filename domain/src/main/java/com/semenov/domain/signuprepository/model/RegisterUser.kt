package com.semenov.domain.signuprepository.model

import java.io.File

data class RegisterUser(
    val name: String,
    val email: String,
    val phone: String,
    val positionId: Int,
    val photo: File,
)