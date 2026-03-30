package de.gello.data.mapper

import de.gello.data.network.response.EntryCardResponse
import de.gello.data.network.response.entryFetch.EntryItemResponse
import de.gello.domain.model.Entry

internal fun EntryCardResponse.toEntry() = Entry(
    id = id,
    journalId = project,
    name = name,
    owner = "",
    createdAt = createdAt,
    updatedAt = updatedAt,
    type = type
)

internal fun EntryItemResponse.toEntryItem() = Entry(
    id = id,
    journalId = project,
    name = name,
    owner = "",
    createdAt = createdAt,
    updatedAt = updatedAt,
    type = type,
    content = content
)