package de.gello.app.feature.overview

import de.gello.domain.model.Journal

sealed interface OverviewState {
    sealed interface Intent

    data object Loading : OverviewState

    data class Default(
        val journals: List<Journal> = emptyList(),
        val showError: Boolean = false
    ) : OverviewState {
        sealed interface Intent : OverviewState.Intent {
            // TODO adding needed intents
        }
    }
}