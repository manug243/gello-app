package de.gello.app.feature.journalDetail

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.onEachOutcome
import de.gello.app.event.UIEvent
import de.gello.app.feature.journalDetail.JournalDetailState.Default
import de.gello.app.feature.journalDetail.JournalDetailState.Intent
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.journal.FetchOneJournalUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class JournalDetailViewModel(
    private val journalId: Int,
    fetchOneJournalUseCase: FetchOneJournalUseCase
) : BaseViewModel<JournalDetailState, Intent>(
    initialState = Default(),
    useEventBus = false
) {

    private val entries = fetchOneJournalUseCase(
        FetchOneJournalUseCase.Params(journalId = journalId)
    ).onEachOutcome(
        onProgress = { setState(JournalDetailState.Loading) },
        onFailure = { showSnackbar(it.message) },
        onSuccess = { data -> setState(Default(journal = data)) }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    override fun executeIntent(intent: Intent) {
        when (intent) {
            Intent.NavigateUp -> dispatchNavigationEvent(NavigationEvent.NavigateUp)
            Default.Intent.AddButton -> sendUIEvent(UIEvent.Snackbar("Not implemented yet"))
        }
    }
}