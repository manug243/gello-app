package de.gello.app.feature.entryDetail

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import com.skash.forge.outcome.onEachOutcome
import de.gello.app.feature.entryDetail.EntryDetailState.Default
import de.gello.app.feature.entryDetail.EntryDetailState.Intent
import de.gello.app.util.BaseViewModel
import de.gello.domain.model.GelEntry
import de.gello.domain.usecase.entry.DeleteEntryUseCase
import de.gello.domain.usecase.entry.FetchOneEntryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class EntryDetailViewModel(
    private val journalId: Int,
    private val entryId: Int,
    fetchOneEntryUseCase: FetchOneEntryUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase
) : BaseViewModel<EntryDetailState, Intent>(
    initialState = Default()
) {

    private val entry = fetchOneEntryUseCase(
        FetchOneEntryUseCase.Params(
            journalId = journalId,
            entryId = entryId
        )
    ).onEachOutcome(
        onProgress = { setState(EntryDetailState.Loading) },
        onFailure = { showSnackbar(it.message) },
        onSuccess = { data ->
            val gelEntry = data.content?.let {
                Json.decodeFromJsonElement<GelEntry>(it)
            }

            setState(
                Default(
                    entry = data,
                    gelEntry = gelEntry
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

            is Default.Intent.DeleteButton -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleDeleteEntry
            )
        }
    }

    private fun handleDeleteEntry(
        state: Default,
        intent: Default.Intent.DeleteButton
    ) {
        viewModelScope.launch {
            deleteEntryUseCase(
                DeleteEntryUseCase.Params(
                    journalId = journalId,
                    entryId = entryId
                )
            ).collectOutcome(
                onProgress = { setState(EntryDetailState.Loading) },
                onFailure = { showSnackbar(it.message) },
                onSuccess = {
                    dispatchNavigationEvent(NavigationEvent.NavigateUp)
                }
            )
        }
    }
}