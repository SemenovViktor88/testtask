package com.semenov.data.users.api

import com.semenov.data.network.ApiContract.Users
import com.semenov.data.users.model.ApiPageUser
import com.semenov.data.users.model.ApiUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {

    @GET(Users.USERS)
    suspend fun getUsers(
        @Query(Users.PAGE) page: Int,
        @Query(Users.COUNT) count: Int,
    ): ApiPageUser

    @GET(Users.USER_ID)
    suspend fun getUser(@Path(Users.ID) userId: Int): ApiUser
}