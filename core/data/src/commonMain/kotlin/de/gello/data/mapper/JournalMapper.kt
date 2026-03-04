package de.gello.data.mapper

import de.gello.data.network.response.JournalResponse
import de.gello.domain.model.Journal

internal fun JournalResponse.toJournal() = Journal(
    id = id,
    title = name,
    color = "",
    owner = "",
    updatedAt = updatedAt,
    entries = emptyList()
)