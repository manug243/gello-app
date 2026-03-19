package de.gello.app.feature.overview

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import com.skash.forge.usecase.invoke
import de.gello.app.event.UIEvent
import de.gello.app.feature.overview.OverviewState.Default
import de.gello.app.navigation.Screen
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.journal.FetchJournalsUseCase
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
                        setState(Default(journals = data, allJournals = data))
                    }
                )
        }
    }

    override fun executeIntent(intent: OverviewState.Intent) {
        when (intent) {
            is OverviewState.Intent.NavigateToOneJournal ->
                dispatchNavigationEvent(
                    NavigationEvent.NavigateTo(Screen.JournalDetails(id = intent.id))
                )

            is Default.Intent.Query -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleQueryIntent
            )
        }
    }

    private fun handleQueryIntent(state: Default, intent: Default.Intent.Query) {
        val query = intent.value

        val filtered = state.allJournals.filter {
            query.isBlank() || it.title.contains(query, ignoreCase = true)
        }

        setState(
            state.copy(
                query = intent.value,
                journals = filtered
            )
        )
    }
}