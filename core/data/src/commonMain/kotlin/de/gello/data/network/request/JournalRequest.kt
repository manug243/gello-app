package de.gello.data.network.request

import kotlinx.serialization.Serializable

@Serializable
internal data class JournalRequest(
    val name: String,
    val description: String,
    val indicatorColor: String
)
