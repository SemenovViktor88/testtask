package com.semenov.data.users

import com.semenov.data.users.api.UsersApi
import com.semenov.data.users.model.mapToDomain
import com.semenov.domain.usersrepository.UsersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
): UsersRepository {
    override suspend fun getUsers(page: Int, count: Int) = usersApi.getUsers(page, count).mapToDomain()
}