package de.gello.app

sealed interface AppState {
    sealed interface Intent

    data object Loading : AppState

    data class Loaded(
        val isLoggedIn: Boolean = false
    ) : AppState {
        sealed interface Intent
    }
}