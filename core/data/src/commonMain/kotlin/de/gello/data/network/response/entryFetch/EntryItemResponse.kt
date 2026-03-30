package de.gello.data.network.response.entryFetch


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull

@Serializable
data class EntryItemResponse(
    @SerialName("content")
    val content: JsonElement? = JsonNull,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("project")
    val project: Int,
    @SerialName("type")
    val type: String,
    @SerialName("updated_at")
    val updatedAt: String
)