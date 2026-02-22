package de.gello.domain.repository

import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.Journal

interface JournalRepository {

    suspend fun fetchJournals(): ApiResponse<List<Journal>>

    suspend fun createJournal(journal: Journal): ApiResponse<Unit>
}