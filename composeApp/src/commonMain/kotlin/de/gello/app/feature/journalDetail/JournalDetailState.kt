package de.gello.app.feature.journalDetail

import de.gello.domain.model.Entry
import de.gello.domain.model.Journal

sealed interface JournalDetailState {
    sealed interface Intent {
        data object NavigateUp : Intent
        data object NavigateToEntryCreation : Intent
        data class NavigateToEntry(val journalId: Int, val entryId: Int) : Intent
    }

    data object Loading : JournalDetailState

    data class Default(
        val journal: Journal = Journal.emptyJournal,
        val query: String = "",
        val allEntries: List<Entry> = emptyList(),
        val showError: Boolean = false
    ) : JournalDetailState {
        sealed interface Intent : JournalDetailState.Intent {
            data object DeleteButton : Intent
            data class Query(val value: String) : Intent
        }
    }
}