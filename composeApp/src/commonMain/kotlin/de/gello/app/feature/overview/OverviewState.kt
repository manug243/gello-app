package de.gello.app.feature.overview

import de.gello.domain.model.Journal

sealed interface OverviewState {
    sealed interface Intent {
        data class NavigateToOneJournal(val id: Int) : Intent
    }

    data object Loading : OverviewState

    data class Default(
        val journals: List<Journal> = emptyList(),
        val allJournals: List<Journal> = emptyList(),
        val query: String = "",
        val showError: Boolean = false
    ) : OverviewState {
        sealed interface Intent : OverviewState.Intent {
            data class Query(val value: String) : Intent
        }
    }
}