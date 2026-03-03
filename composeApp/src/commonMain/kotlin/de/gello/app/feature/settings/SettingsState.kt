package de.gello.app.feature.settings

sealed interface SettingsState {
    sealed interface Intent

    data object Loading : SettingsState

    data class Default(
        val showError: Boolean = false
    ) : SettingsState {
        sealed interface Intent : SettingsState.Intent {
            data object Logout : Intent
        }
    }
}