package de.gello.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Entry(
    val id: Int? = null,
    val journalId: Int? = null,
    val name: String,
    val owner: String? = null,
    val createdAt: String? = "",
    val updatedAt: String? = "",
    val type: String? = null,
    val content: JsonElement? = null
) {
    companion object {
        val emptyEntry = Entry(
            name = "",
            owner = "",
            createdAt = "",
            updatedAt = ""
        )
    }
}
