package com.semenov.data.signup.api

import com.semenov.data.network.ApiContract.Users
import com.semenov.data.signup.model.ApiPositions
import com.semenov.data.signup.model.ApiRegisterResponse
import com.semenov.data.signup.model.ApiRegisterUser
import com.semenov.data.signup.model.ApiToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SignUpApi {

    @GET(Users.TOKEN)
    suspend fun getToken(): ApiToken

    @GET(Users.POSITIONS)
    suspend fun getPosition(): ApiPositions

    @POST(Users.USERS)
    suspend fun registerUser(@Header(Users.TOKEN_HEADER) token: String, @Body request: ApiRegisterUser): ApiRegisterResponse
}