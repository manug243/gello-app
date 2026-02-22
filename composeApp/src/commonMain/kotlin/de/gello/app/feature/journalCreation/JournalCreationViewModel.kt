package de.gello.app.feature.journalCreation

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import de.gello.app.util.BaseViewModel
import de.gello.domain.model.Journal
import de.gello.domain.usecase.journal.CreateJournalUseCase
import kotlinx.coroutines.launch

class JournalCreationViewModel(
    private val createJournalUseCase: CreateJournalUseCase
) : BaseViewModel<JournalCreationState, JournalCreationState.Intent>(
    initialState = JournalCreationState.Default(),
    useEventBus = false
) {
    override fun executeIntent(intent: JournalCreationState.Intent) {
        when (intent) {
            is JournalCreationState.Intent.NavigateUp ->
                dispatchNavigationEvent(NavigationEvent.NavigateUp)

            is JournalCreationState.Default.Intent.SetJournalTitleIntent ->
                copyJournal {
                    copy(title = intent.value)
                }

            is JournalCreationState.Default.Intent.CreateJournalIntent -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleCreateJournalIntent
            )
        }
    }

    private inline fun copyJournal(
        crossinline block: Journal.() -> Journal
    ) {
        reduceState<JournalCreationState.Default> {
            copy(journal = block(journal))
        }
    }

    private fun handleCreateJournalIntent(
        state: JournalCreationState.Default,
        intent: JournalCreationState.Default.Intent.CreateJournalIntent
    ) {

        viewModelScope.launch {
            createJournalUseCase(
                CreateJournalUseCase.Params(
                    journal = state.journal
                )
            ).collectOutcome(
                onProgress = { setState(JournalCreationState.Loading) },
                onFailure = {
                    setState(state.copy(showError = true))
                },
                onSuccess = {

                    executeIntent(JournalCreationState.Intent.NavigateUp)
                }
            )
        }
    }
}