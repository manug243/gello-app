package de.gello.app

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import de.gello.app.event.UIEvent
import de.gello.app.navigation.Screen
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.ObserveIsUserLoggedInUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    observeIsUserLoggedInUseCase: ObserveIsUserLoggedInUseCase
) : BaseViewModel<AppState, AppState.Intent>(AppState.Loading) {

    private val isLoggedIn = observeIsUserLoggedInUseCase(Unit)
        .onEach {
            setState(AppState.Loaded(isLoggedIn = it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    override fun executeIntent(intent: AppState.Intent) {
        when (intent) {
            AppState.Intent.NavigateToCreateJournal ->
               dispatchNavigationEvent(NavigationEvent.NavigateTo(Screen.JournalCreation))
        }
    }
}