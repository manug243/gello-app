package de.gello.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Lane(
    val lane: String,
    val probe: String,
    val volume: Int? = null
)