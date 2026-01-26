package de.gello.app.feature.overview

sealed interface OverviewState {
    sealed interface Intent {
        // TODO navigate to project
    }

    data object Loading : OverviewState

    data class Default(
        val projects: List<String> = emptyList(), //needs to be changed from String to future "project" model
        val showError: Boolean = false
    ) : OverviewState {
        sealed interface Intent : OverviewState.Intent {
            // TODO adding needed intents
        }
    }
}