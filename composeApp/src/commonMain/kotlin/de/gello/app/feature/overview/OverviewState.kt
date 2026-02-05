package de.gello.app.feature.overview

import de.gello.domain.model.Journal

sealed interface OverviewState {
    sealed interface Intent {
        data object NavigateToCreateProject : Intent
    }

    data object Loading : OverviewState

    data class Default(
        val journals: List<Journal> = Journal.mocks, // need to change back to emptyList() when done testing
        val showError: Boolean = false
    ) : OverviewState {
        sealed interface Intent : OverviewState.Intent {
            // TODO adding needed intents
        }
    }
}