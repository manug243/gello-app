package de.gello.data.mapper

import de.gello.data.network.response.EntryResponse
import de.gello.domain.model.Entry

internal fun EntryResponse.toEntry() = Entry(
    id = id,
    journalId = project,
    name = name,
    owner = "",
    createdAt = createdAt,
    updatedAt = updatedAt
)

