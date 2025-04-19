package com.semenov.testtask.model

import com.semenov.domain.signuprepository.model.Position
import com.semenov.domain.signuprepository.model.Positions

data class AppPositions (
    val success: Boolean,
    val positions: List<AppPosition>,
)

data class AppPosition (
    val id: Int,
    val name: String,
)

fun Positions.mapToApp() = AppPositions(
    success = success,
    positions = positions.map { it.mapToApp() }
)

fun Position.mapToApp() = AppPosition(
    id = id,
    name = name
)