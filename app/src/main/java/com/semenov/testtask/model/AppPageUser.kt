package com.semenov.testtask.model

import com.semenov.domain.usersrepository.model.Links
import com.semenov.domain.usersrepository.model.PageUser
import com.semenov.domain.usersrepository.model.User

data class AppPageUser(
    val success: Boolean,
    val totalPages: Int,
    val totalUsers: Int,
    val count: Int,
    val page: Int,
    val links: AppLinks,
    val users: List<AppUser>
) {
    companion object {

        fun initial() = AppPageUser(
            success = false,
            totalPages = 0,
            totalUsers = 0,
            count = 0,
            page = 0,
            links = AppLinks.initialLinks(),
            users = emptyList()
        )
    }
}

data class AppLinks(
    val nextUrl: String?,
    val prevUrl: String?
) {
    companion object {
        fun initialLinks() = AppLinks(null, null)
    }
}

data class AppUser(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val position: String,
    val positionId: Int,
    val registrationTimestamp : Int,
    val photo: String
)

fun PageUser.mapToApp() = AppPageUser(
    success = success,
    totalPages = totalPages,
    totalUsers = totalUsers,
    count = count,
    page = page,
    links = links.mapToApp(),
    users = users.map { it.mapToApp() }
)

fun Links.mapToApp() = AppLinks(
    nextUrl = nextUrl,
    prevUrl = prevUrl
)

fun User.mapToApp() = AppUser(
    id = id,
    name = name,
    email = email,
    phone = phone,
    position = position,
    positionId = positionId,
    registrationTimestamp = registrationTimestamp,
    photo = photo
)