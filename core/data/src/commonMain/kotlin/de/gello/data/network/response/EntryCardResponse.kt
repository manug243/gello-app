package de.gello.data.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntryCardResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("project")
    val project: Int? = null,
    @SerialName("name")
    val name: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("type")
    val type: String? = null
)