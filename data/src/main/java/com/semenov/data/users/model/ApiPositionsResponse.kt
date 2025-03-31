package com.semenov.data.users.model

import com.google.gson.annotations.SerializedName

data class ApiPositionsResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("positions") val positions: List<Position>,
)

data class Position (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)
