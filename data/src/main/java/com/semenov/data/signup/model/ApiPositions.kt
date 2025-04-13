package com.semenov.data.signup.model

import com.google.gson.annotations.SerializedName
import com.semenov.domain.signuprepository.model.Position
import com.semenov.domain.signuprepository.model.Positions

data class ApiPositions (
    @SerializedName("success") val success: Boolean,
    @SerializedName("positions") val positions: List<ApiPosition>,
)

data class ApiPosition (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)

fun ApiPositions.mapToDomain() = Positions(
    success = success,
    positions = positions.map { it.mapToDomain() }
)

fun ApiPosition.mapToDomain() = Position(
    id = id,
    name = name
)
