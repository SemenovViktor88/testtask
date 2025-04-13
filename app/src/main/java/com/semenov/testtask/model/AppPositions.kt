package com.semenov.testtask.model

data class AppPositions (
    val success: Boolean,
    val positions: List<AppPosition>,
)

data class AppPosition (
    val id: Int,
    val name: String,
)