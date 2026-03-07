package de.gello.app.feature.journalCreation

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import com.skash.forge.outcome.onEachOutcome
import com.skash.forge.usecase.invoke
import de.gello.app.util.BaseViewModel
import de.gello.domain.model.Journal
import de.gello.domain.usecase.FetchUserUseCase
import de.gello.domain.usecase.journal.CreateJournalUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalCreationViewModel(
    fetchUserUseCase: FetchUserUseCase,
    private val createJournalUseCase: CreateJournalUseCase
) : BaseViewModel<JournalCreationState, JournalCreationState.Intent>(
    initialState = JournalCreationState.Default(),
    useEventBus = false
) {
    private val userData = fetchUserUseCase()
        .onEachOutcome(
            onProgress = { setState(JournalCreationState.Loading) },
            onFailure = { showSnackbar(it.message) },
            onSuccess = { user ->
                setState(
                    JournalCreationState.Default(
                        user = user
                    )
                )
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    override fun executeIntent(intent: JournalCreationState.Intent) {
        when (intent) {
            is JournalCreationState.Intent.NavigateUp ->
                dispatchNavigationEvent(NavigationEvent.NavigateUp)

            is JournalCreationState.Default.Intent.SetJournalTitleIntent ->
                copyJournal {
                    copy(title = intent.value)
                }

            is JournalCreationState.Default.Intent.SetJournalDescriptionIntent ->
                copyJournal {
                    copy(description = intent.value)
                }

            is JournalCreationState.Default.Intent.SetJournalColorIntent ->
                copyJournal {
                    copy(color = intent.value)
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
            copy(journal = block(journal), showError = false)
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