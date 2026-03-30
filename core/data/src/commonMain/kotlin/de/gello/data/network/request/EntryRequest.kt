package de.gello.data.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class EntryRequest(
    @SerialName("name")
    val name: String,

    @SerialName("type")
    val type: String?,

    @SerialName("content")
    val content: JsonElement
)