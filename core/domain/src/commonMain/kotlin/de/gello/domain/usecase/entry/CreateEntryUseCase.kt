package de.gello.domain.usecase.entry

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.model.Entry
import de.gello.domain.repository.JournalRepository
import kotlinx.coroutines.flow.FlowCollector

class CreateEntryUseCase(
    private val journalRepository: JournalRepository
) : FlowOutcomeUseCase<CreateEntryUseCase.Params, Unit, ErrorType>() {

    data class Params(
        val entry: Entry
    )

    override suspend fun FlowCollector<Outcome<Unit, ErrorType>>.execute(
        params: Params
    ) {
        emitFrom(
            journalRepository.createEntry(
                journalId = params.entry.journalId ?: 0,
                entry = params.entry
            )
        ) {
            toErrorType { ErrorType.Custom("Something went wrong.") }
        }
    }
}