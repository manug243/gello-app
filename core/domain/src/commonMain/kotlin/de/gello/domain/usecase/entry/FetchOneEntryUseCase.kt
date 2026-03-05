package de.gello.domain.usecase.entry

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.model.Entry
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class FetchOneEntryUseCase(
    private val journalRepository: JournalRepository
) : FlowOutcomeUseCase<FetchOneEntryUseCase.Params, Entry, ErrorType>() {

    data class Params(
        val journalId: Int,
        val entryId: Int
    )

    override suspend fun FlowCollector<Outcome<Entry, ErrorType>>.execute(params: Params) {
        val result = journalRepository.fetchEntry(
            journalId = params.journalId,
            entryId = params.entryId
        )

        emitFrom(result) {
            toErrorType { ErrorType.FetchEntryFailed }
        }
    }
}