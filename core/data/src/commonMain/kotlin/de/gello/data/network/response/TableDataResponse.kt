package de.gello.data.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TableDataResponse(
    @SerialName("lane")
    val lane: String,
    @SerialName("sample")
    val sample: String,
    @SerialName("volume")
    val volume: String? = null
)