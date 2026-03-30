package de.gello.app.feature.entryDetail

import de.gello.domain.model.Entry
import de.gello.domain.model.GelEntry

sealed interface EntryDetailState {
    sealed interface Intent {
        data object NavigateUp : Intent
    }

    data object Loading : EntryDetailState

    data class Default(
        val entry: Entry = Entry.emptyEntry,
        val gelEntry: GelEntry? = null,
        val showError: Boolean = false
    ) : EntryDetailState {
        sealed interface Intent : EntryDetailState.Intent {
            data object DeleteButton : Intent
        }
    }
}