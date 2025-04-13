package com.semenov.data.signup

import com.semenov.data.signup.api.SignUpApi
import com.semenov.data.signup.model.mapToApi
import com.semenov.data.signup.model.mapToDomain
import com.semenov.domain.signuprepository.SignUpRepository
import com.semenov.domain.signuprepository.model.RegisterUser
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api: SignUpApi
): SignUpRepository {

    override var cachedToken: String? = null

    override suspend fun getToken(): String {
        if (cachedToken != null) return cachedToken!!
        val response = api.getToken()
        if (response.success) {
            cachedToken = response.token
            return response.token
        } else throw Exception("Token fetch failed")
    }

    override suspend fun getPositions() = api.getPosition().mapToDomain()

    override suspend fun registerUser(token: String, user: RegisterUser) = api.registerUser(token, user.mapToApi()).mapToDomain()
}