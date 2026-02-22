package de.gello.data.repository

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.client.execute
import com.skash.forge.network.response.ApiResponse
import de.gello.data.mapper.toJournal
import de.gello.data.network.endpoint.Api
import de.gello.data.network.request.JournalRequest
import de.gello.data.network.response.JournalResponse
import de.gello.domain.model.Journal
import de.gello.domain.repository.JournalRepository

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
                body(JournalRequest(name = journal.title))
            }
        )


}