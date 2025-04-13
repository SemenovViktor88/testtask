package com.semenov.testtask.model

data class AppRegisterResponse(
    val success: Boolean,
    val userId: Int,
    val message: String
)