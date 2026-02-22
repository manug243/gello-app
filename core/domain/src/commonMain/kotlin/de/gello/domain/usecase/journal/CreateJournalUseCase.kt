package de.gello.domain.usecase.journal

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.model.Journal
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class CreateJournalUseCase(
    private val journalRepository: JournalRepository
) : FlowOutcomeUseCase<CreateJournalUseCase.Params, Unit, ErrorType>() {

    data class Params(
        val journal: Journal
    )

    override suspend fun FlowCollector<Outcome<Unit, ErrorType>>.execute(
        params: Params
    ) {
        val result = journalRepository.createJournal(params.journal)

        emitFrom(result) {
            toErrorType { ErrorType.CreateJournalFailed }
        }
    }
}