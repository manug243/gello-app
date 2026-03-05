package de.gello.app.feature.entryDetail

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.onEachOutcome
import de.gello.app.feature.entryDetail.EntryDetailState.Default
import de.gello.app.feature.entryDetail.EntryDetailState.Intent
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.entry.FetchOneEntryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class EntryDetailViewModel(
    private val journalId: Int,
    private val entryId: Int,
    fetchOneEntryUseCase: FetchOneEntryUseCase
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
            setState(
                Default(entry = data)
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
        }
    }

}