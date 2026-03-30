package de.gello.data.repository

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.client.execute
import com.skash.forge.network.response.ApiResponse
import de.gello.data.mapper.toEntry
import de.gello.data.mapper.toEntryItem
import de.gello.data.mapper.toJournal
import de.gello.data.network.endpoint.Api
import de.gello.data.network.request.EntryRequest
import de.gello.data.network.request.JournalRequest
import de.gello.data.network.response.EntryCardResponse
import de.gello.data.network.response.JournalResponse
import de.gello.data.network.response.entryFetch.EntryItemResponse
import de.gello.domain.model.Entry
import de.gello.domain.model.Journal
import de.gello.domain.repository.JournalRepository
import kotlinx.serialization.json.JsonNull

class JournalRepositoryImpl(
    private val httpClient: HttpClient
) : JournalRepository {

    override suspend fun fetchJournals(): ApiResponse<List<Journal>> =
        httpClient.execute<List<JournalResponse>, List<Journal>>(
            mapper = { response ->
                response.map { it.toJournal() }
            },
            requestBuilder = {
                get(Api.Project)
            }
        )

    override suspend fun createJournal(journal: Journal): ApiResponse<Unit> =
        httpClient.execute<Unit, Unit>(
            mapper = {},
            requestBuilder = {
                post(Api.Project.Create)
                body(JournalRequest(
                    name = journal.title,
                    description = journal.description.orEmpty(),
                    indicatorColor = journal.color
                ))
            }
        )

    override suspend fun deleteJournal(journalId: Int): ApiResponse<Unit> =
        httpClient.execute<Unit, Unit>(
            mapper = {},
            requestBuilder = {
                delete(Api.Project.Delete(journalId))
            }
        )

    override suspend fun fetchJournal(id: Int): ApiResponse<Journal> =
        httpClient.execute<JournalResponse, Journal>(
            mapper = { response ->
                response.toJournal()
            },
            requestBuilder = {
                get(Api.Project.Get(id))
            }
        )

    override suspend fun fetchEntries(id: Int): ApiResponse<List<Entry>> =
        httpClient.execute<List<EntryCardResponse>, List<Entry>>(
            mapper = { response ->
                response.map { it.toEntry() }
            },
            requestBuilder = {
                get(Api.Project.Entries(id))
            }
        )

    override suspend fun fetchEntry(journalId: Int, entryId: Int): ApiResponse<Entry> =
        httpClient.execute<EntryItemResponse, Entry>(
            mapper = { it.toEntryItem() },
            requestBuilder = {
                get(Api.Project.Entry(journalId, entryId))
            }
        )

    override suspend fun createEntry(journalId: Int, entry: Entry): ApiResponse<Unit> =
        httpClient.execute<Unit, Unit>(
            mapper = {},
            requestBuilder = {
                post(Api.Project.CreateEntry(journalId))
                body(
                    EntryRequest(
                        name = entry.name,
                        type = entry.type,
                        content = entry.content ?: JsonNull
                    )
                )
            }
        )

    override suspend fun deleteEntry(journalId: Int, entryId: Int): ApiResponse<Unit> =
        httpClient.execute<Unit, Unit>(
            mapper = {},
            requestBuilder = {
                delete(Api.Project.DeleteEntry(journalId, entryId))
            }
        )
}