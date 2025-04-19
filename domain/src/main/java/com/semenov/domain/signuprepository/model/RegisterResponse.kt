package com.semenov.domain.signuprepository.model

sealed class RegisterResponseState {

    data class Success(val success: Boolean, val userId: Int, val message: String): RegisterResponseState()

    // 401 Unauthorized
    data class ErrorToken(val success: Boolean = false, val message: String): RegisterResponseState()

    // 409 Conflict (duplicate email or phone)
    data class ErrorNameOrEmail(val success: Boolean = false, val message: String): RegisterResponseState()

    // 422 Unprocessable Entity
    data class ErrorRegisterForm(val message: String, val mapErrors: Map<String, List<String>>): RegisterResponseState()

    // Unknown Errors
    data class UnknownError(val message: String): RegisterResponseState()
}