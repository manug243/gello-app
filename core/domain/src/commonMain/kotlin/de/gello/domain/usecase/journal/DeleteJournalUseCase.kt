package de.gello.domain.usecase.journal

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class DeleteJournalUseCase(
    private val journalRepository: JournalRepository
) : FlowOutcomeUseCase<DeleteJournalUseCase.Params, Unit, ErrorType>() {

    data class Params(
        val journalId: Int
    )

    override suspend fun FlowCollector<Outcome<Unit, ErrorType>>.execute(params: Params) {
        val result = journalRepository.deleteJournal(params.journalId)

        emitFrom(result) {
            toErrorType { ErrorType.DeleteError }
        }
    }
}