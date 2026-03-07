package de.gello.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Journal(
    val id: Int,
    val title: String,
    val description: String?,
    val color: String,
    val owner: User?,
    val updatedAt: String,
    val entryCount: Int?,
    val entries: List<Entry>
) {
    companion object {

        val emptyJournal = Journal(
            id = -1,
            title = "",
            description = "",
            color = "",
            owner = User(
                id = -1,
                username = "",
                firstname = "",
                lastname = ""
            ),
            updatedAt = "",
            entryCount = 0,
            entries = emptyList()
        )
    }
}
