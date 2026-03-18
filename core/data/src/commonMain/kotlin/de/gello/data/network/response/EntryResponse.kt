package de.gello.data.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntryResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("project")
    val project: Int? = null, // need to remove null possibility when be updated missing projectId
    @SerialName("name")
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("type")
    val type: Int? = null
)