package de.gello.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Journal(
    val id: Int,
    val title: String,
    val description: String? = null,
    val color: String,
    val owner: String,
    val updatedAt: String,
    val entries: List<Entry>
) {
    companion object {

        val emptyJournal = Journal(
            id = -1,
            title = "",
            color = "",
            owner = "",
            updatedAt = "",
            entries = emptyList()
        )
    }
}
