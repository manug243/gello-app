package de.gello.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JournalResponse(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("owner")
    val owner: UserResponse? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("indicatorColor")
    val indicatorColor: String,
    @SerialName("entry_count")
    val entryCount: Int? = null,
    @SerialName("updated_at")
    val updatedAt: String
)