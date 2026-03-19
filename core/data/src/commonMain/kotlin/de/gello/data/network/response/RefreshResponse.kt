package de.gello.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    @SerialName("access")
    val access: String,
    @SerialName("refresh")
    val refresh: String
)
