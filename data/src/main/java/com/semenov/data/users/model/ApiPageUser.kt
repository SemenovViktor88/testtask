package com.semenov.data.users.model

import com.google.gson.annotations.SerializedName
import com.semenov.domain.usersrepository.model.Links
import com.semenov.domain.usersrepository.model.PageUser
import com.semenov.domain.usersrepository.model.User

data class ApiPageUser(
    @SerializedName("success") val success: Boolean,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_users") val totalUsers: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("links") val links: ApiLinks,
    @SerializedName("users") val users: List<ApiUser>
)

data class ApiLinks(
    @SerializedName("next_url") val nextUrl: String?,
    @SerializedName("prev_url") val prevUrl: String?
)

data class ApiUser(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("position") val position: String,
    @SerializedName("position_id") val positionId: Int,
    @SerializedName("registration_timestamp") val registrationTimestamp : Int,
    @SerializedName("photo") val photo: String
)

fun ApiPageUser.mapToDomain() = PageUser(
    success = success,
    totalPages = totalPages,
    totalUsers = totalUsers,
    count = count,
    page = page,
    links = links.mapToDomain(),
    users = users.map { it.mapToDomain() }
)

fun ApiLinks.mapToDomain() = Links(
    nextUrl = nextUrl,
    prevUrl = prevUrl
)

fun ApiUser.mapToDomain() = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    position = position,
    positionId = positionId,
    registrationTimestamp = registrationTimestamp,
    photo = photo
)