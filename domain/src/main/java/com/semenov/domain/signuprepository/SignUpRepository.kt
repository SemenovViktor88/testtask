package com.semenov.domain.signuprepository

import com.semenov.domain.signuprepository.model.Positions
import com.semenov.domain.signuprepository.model.RegisterResponse
import com.semenov.domain.signuprepository.model.RegisterUser

interface SignUpRepository {
    var cachedToken: String?
    suspend fun getToken(): String
    suspend fun getPositions(): Positions
    suspend fun registerUser(token: String, user: RegisterUser): RegisterResponse
}