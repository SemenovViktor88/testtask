package com.semenov.domain.signuprepository.model

data class Positions (
    val success: Boolean,
    val positions: List<Position>,
)

data class Position (
    val id: Int,
    val name: String,
)