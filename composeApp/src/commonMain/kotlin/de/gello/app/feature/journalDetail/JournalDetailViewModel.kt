package de.gello.app.feature.journalDetail

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import com.skash.forge.outcome.onEachOutcome
import com.skash.forge.usecase.invoke
import de.gello.app.event.UIEvent
import de.gello.app.feature.journalCreation.JournalCreationState
import de.gello.app.feature.journalDetail.JournalDetailState.Default
import de.gello.app.feature.journalDetail.JournalDetailState.Intent
import de.gello.app.navigation.Screen
import de.gello.app.util.BaseViewModel
import de.gello.domain.model.User
import de.gello.domain.usecase.FetchUserUseCase
import de.gello.domain.usecase.journal.DeleteJournalUseCase
import de.gello.domain.usecase.journal.FetchOneJournalUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalDetailViewModel(
    private val journalId: Int,
    private val fetchOneJournalUseCase: FetchOneJournalUseCase,
    private val deleteJournalUseCase: DeleteJournalUseCase,
    fetchUserUseCase: FetchUserUseCase
) : BaseViewModel<JournalDetailState, Intent>(
    initialState = Default(),
    useEventBus = false
) {
    private var loadedUser: User? = null

    private val userData = fetchUserUseCase()
        .onEachOutcome(
            onProgress = { setState(JournalDetailState.Loading) },
            onFailure = { showSnackbar(it.message) },
            onSuccess = { user ->
                loadedUser = user
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    internal fun refreshEntries() {
        viewModelScope.launch {
            fetchOneJournalUseCase(
                FetchOneJournalUseCase.Params(journalId = journalId)
            ).collectOutcome(
                onFailure = {
                    sendUIEvent(UIEvent.Snackbar(it.message))
                },
                onSuccess = { data ->
                    setState(
                        Default(
                            journal = data,
                            user = loadedUser ?: User.emptyUser,
                            allEntries = data.entries
                        )
                    )
                }
            )
        }
    }

    private val entries = fetchOneJournalUseCase(
        FetchOneJournalUseCase.Params(journalId = journalId)
    ).onEachOutcome(
        onProgress = { setState(JournalDetailState.Loading) },
        onFailure = { showSnackbar(it.message) },
        onSuccess = { data ->
            setState(
                Default(
                    journal = data,
                    user = loadedUser ?: User.emptyUser,
                    allEntries = data.entries
                )
            )
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.NavigateUp -> dispatchNavigationEvent(NavigationEvent.NavigateUp)

            is Intent.NavigateToEntryCreation ->
                dispatchNavigationEvent(
                    NavigationEvent.NavigateTo(Screen.EntryCreation(journalId))
                )

            is Intent.NavigateToEntry -> dispatchNavigationEvent(
                NavigationEvent.NavigateTo(
                    Screen.EntryDetails(
                        journalId = intent.journalId,
                        entryId = intent.entryId
                    )
                )
            )

            is Default.Intent.DeleteButton -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleDeleteJournal
            )

            is Default.Intent.Query -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleQueryIntent
            )
        }
    }

    private fun handleQueryIntent(state: Default, intent: Default.Intent.Query) {
        val query = intent.value
        val source = state.journal.entries

        val filtered = source.filter {
            query.isBlank() || it.name.contains(query, ignoreCase = true)
        }

        setState(
            state.copy(
                query = intent.value,
                allEntries = filtered
            )
        )
    }

    private fun handleDeleteJournal(
        state: Default,
        intent: Default.Intent.DeleteButton
    ) {
        viewModelScope.launch {
            deleteJournalUseCase(
                DeleteJournalUseCase.Params(
                    journalId = journalId
                )
            ).collectOutcome(
                onProgress = { setState(JournalDetailState.Loading) },
                onFailure = { showSnackbar(it.message) },
                onSuccess = {
                    dispatchNavigationEvent(NavigationEvent.NavigateUp)
                }
            )
        }
    }
}