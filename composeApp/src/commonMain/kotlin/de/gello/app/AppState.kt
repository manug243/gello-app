package de.gello.app

sealed interface AppState {
    sealed interface Intent {
        data object NavigateToCreateJournal : Intent
    }

    data object Loading : AppState

    data class Loaded(
        val isLoggedIn: Boolean
    ) : AppState {
        sealed interface Intent
    }
}