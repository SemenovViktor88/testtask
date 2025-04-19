package com.semenov.data.signup.api

import com.semenov.data.network.ApiContract.Users
import com.semenov.data.signup.model.ApiPositions
import com.semenov.data.signup.model.ApiRegisterResponse
import com.semenov.data.signup.model.ApiToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignUpApi {

    @GET(Users.TOKEN)
    suspend fun getToken(): ApiToken

    @GET(Users.POSITIONS)
    suspend fun getPosition(): ApiPositions

    @Multipart
    @POST(Users.USERS)
    suspend fun registerUser(
        @Header(Users.TOKEN_HEADER) token: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("position_id") positionId: RequestBody,
        @Part photo: MultipartBody.Part
    ): Response<ApiRegisterResponse>
}