package de.gello.domain.repository

import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.Entry
import de.gello.domain.model.Journal

interface JournalRepository {

    suspend fun fetchJournals(): ApiResponse<List<Journal>>

    suspend fun fetchJournal(id: Int): ApiResponse<Journal>

    suspend fun fetchEntries(id: Int): ApiResponse<List<Entry>>

    suspend fun createJournal(journal: Journal): ApiResponse<Unit>

    suspend fun deleteJournal(journalId: Int): ApiResponse<Unit>

    suspend fun fetchEntry(journalId: Int, entryId: Int): ApiResponse<Entry>

    suspend fun deleteEntry(journalId: Int, entryId: Int): ApiResponse<Unit>
}