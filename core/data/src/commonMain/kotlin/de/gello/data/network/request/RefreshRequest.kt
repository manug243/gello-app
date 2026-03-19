package de.gello.data.network.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refresh: String
)