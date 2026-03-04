package de.gello.domain.usecase.journal

import com.skash.forge.network.response.zip
import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.model.Journal
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class FetchOneJournalUseCase(
    private val journalRepository: JournalRepository,
) : FlowOutcomeUseCase<FetchOneJournalUseCase.Params, Journal, ErrorType>() {

    data class Params(
        val journalId: Int
    )

    override suspend fun FlowCollector<Outcome<Journal, ErrorType>>.execute(
        params: Params
    ) {
        val result = zip(
            { journalRepository.fetchJournal(params.journalId) },
            { journalRepository.fetchEntries(params.journalId) }
        ) { journal, entries ->

            journal.copy(
                entries = entries
            )
        }

        emitFrom(result) {
            toErrorType { ErrorType.FetchEntriesFailed }
        }
    }
}