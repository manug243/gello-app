package de.gello.data.mapper

import de.gello.data.network.response.JournalResponse
import de.gello.domain.model.Journal
import de.gello.domain.model.User

internal fun JournalResponse.toJournal() = Journal(
    id = id,
    title = name,
    description = description,
    color = indicatorColor,
    owner = owner?.let {
        User(
            id = it.id,
            username = it.username,
            firstname = it.firstname,
            lastname = it.lastname
        )
    },
    updatedAt = updatedAt,
    entryCount = entryCount,
    entries = emptyList()
)