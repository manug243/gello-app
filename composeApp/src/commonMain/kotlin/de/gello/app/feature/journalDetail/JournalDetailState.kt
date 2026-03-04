package de.gello.app.feature.journalDetail

import de.gello.domain.model.Journal

sealed interface JournalDetailState {
    sealed interface Intent {
        data object NavigateUp : Intent
    }

    data object Loading : JournalDetailState

    data class Default(
        val journal: Journal = Journal.emptyJournal,
        val showError: Boolean = false
    ) : JournalDetailState {
        sealed interface Intent : JournalDetailState.Intent {

        }
    }
}