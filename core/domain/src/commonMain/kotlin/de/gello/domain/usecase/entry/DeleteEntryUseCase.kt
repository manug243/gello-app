package de.gello.domain.usecase.entry

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class DeleteEntryUseCase(
    private val journalRepository: JournalRepository
) : FlowOutcomeUseCase<DeleteEntryUseCase.Params, Unit, ErrorType>() {

    data class Params(
        val journalId: Int,
        val entryId: Int
    )

    override suspend fun FlowCollector<Outcome<Unit, ErrorType>>.execute(params: Params) {
        val result = journalRepository.deleteEntry(
            journalId = params.journalId,
            entryId = params.entryId
        )

        emitFrom(result) {
            toErrorType { ErrorType.DeleteError }
        }
    }
}