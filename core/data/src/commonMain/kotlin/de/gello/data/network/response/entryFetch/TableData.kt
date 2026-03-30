package de.gello.data.network.response.entryFetch


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TableData(
    @SerialName("lane")
    val lane: String,
    @SerialName("probe")
    val probe: String,
    @SerialName("volume")
    val volume: Int
)