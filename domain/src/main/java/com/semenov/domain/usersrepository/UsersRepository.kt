package com.semenov.domain.usersrepository

import com.semenov.domain.usersrepository.model.PageUser

interface UsersRepository {
    suspend fun getUsers(page: Int, count: Int): PageUser
}