package de.gello.domain.model

data class Entry(
    val id: Int,
    val journalId: Int? = null,
    val name: String,
    val owner: String,
    val createdAt: String,
    val updatedAt: String? = ""
) {
    companion object {
        val emptyEntry = Entry(
            id = -1,
            journalId = null,
            name = "",
            owner = "",
            createdAt = "",
            updatedAt = ""
        )
    }
}
