package de.gello.app.feature.overview

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import com.skash.forge.outcome.onEachOutcome
import com.skash.forge.usecase.invoke
import de.gello.app.event.UIEvent
import de.gello.app.feature.overview.OverviewState.Default
import de.gello.app.navigation.Screen
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.journal.FetchJournalsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val fetchJournalsUseCase: FetchJournalsUseCase
) : BaseViewModel<OverviewState, OverviewState.Intent>(
    initialState = Default(),
    useEventBus = false
) {
    internal fun refreshJournals() {
        viewModelScope.launch {
            fetchJournalsUseCase()
                .collectOutcome(
                    onFailure = {
                        sendUIEvent(UIEvent.Snackbar(it.message))
                    },
                    onSuccess = { data ->
                        setState(Default(journals = data))
                    }
                )
        }
    }

    private val journals = fetchJournalsUseCase()
        .onEachOutcome(
            onProgress = { setState(OverviewState.Loading) },
            onFailure = {
                showSnackbar(it.message)
            },
            onSuccess = { data ->
                setState(Default(journals = data))
            }
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    override fun executeIntent(intent: OverviewState.Intent) {
        when (intent) {
            is OverviewState.Intent.NavigateToOneJournal -> {
                dispatchNavigationEvent(
                    NavigationEvent.NavigateTo(Screen.JournalDetails(id = intent.id))
                )
            }
        }
    }
}