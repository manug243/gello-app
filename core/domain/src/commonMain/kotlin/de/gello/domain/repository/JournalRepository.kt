package de.gello.domain.repository

import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.Entry
import de.gello.domain.model.Journal

interface JournalRepository {

    suspend fun fetchJournals(): ApiResponse<List<Journal>>

    suspend fun fetchJournal(id: Int): ApiResponse<Journal>

    suspend fun fetchEntries(id: Int): ApiResponse<List<Entry>>

    suspend fun createJournal(journal: Journal): ApiResponse<Unit>
}