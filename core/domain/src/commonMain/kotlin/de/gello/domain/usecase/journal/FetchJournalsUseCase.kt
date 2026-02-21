package de.gello.domain.usecase.journal

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.model.Journal
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class FetchJournalsUseCase(
    private val journalRepository: JournalRepository
) : FlowOutcomeUseCase<Unit, List<Journal>, ErrorType>() {

    override suspend fun FlowCollector<Outcome<List<Journal>, ErrorType>>.execute(
        params: Unit
    ) {
        emitFrom(journalRepository.fetchJournals()) {
            toErrorType { ErrorType.FetchJournalsFailed }
        }
    }
}