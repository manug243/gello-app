package de.gello.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GelEntry(
    val image: String,
    val processedImage: String,
    val laneCount: Int,
    val tableData: List<Lane>,
    val note: String
)