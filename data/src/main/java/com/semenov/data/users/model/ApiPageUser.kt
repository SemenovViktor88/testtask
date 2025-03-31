package com.semenov.data.users.model

import com.google.gson.annotations.SerializedName

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
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("position") var position: String,
    @SerializedName("position_id") var positionId: Int,
    @SerializedName("registration_timestamp") var registrationTimestamp : Int,
    @SerializedName("photo") var photo: String
)