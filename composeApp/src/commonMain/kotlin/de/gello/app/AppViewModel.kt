package de.gello.app

import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.ObserveIsUserLoggedInUseCase

class AppViewModel(
    observeIsUserLoggedInUseCase: ObserveIsUserLoggedInUseCase
) : BaseViewModel<AppState, AppState.Intent>(AppState.Loading) {

    private val isLoggedIn = observeIsUserLoggedInUseCase(Unit)

    override fun executeIntent(intent: AppState.Intent) {}
}