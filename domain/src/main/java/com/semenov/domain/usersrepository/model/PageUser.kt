package com.semenov.domain.usersrepository.model

data class PageUser(
    val success: Boolean,
    val totalPages: Int,
    val totalUsers: Int,
    val count: Int,
    val page: Int,
    val links: Links,
    val users: List<User>
)

data class Links(
    val nextUrl: String?,
    val prevUrl: String?
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val position: String,
    val positionId: Int,
    val registrationTimestamp : Int,
    val photo: String
)