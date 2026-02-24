package de.gello.app.feature.journalCreation

import de.gello.domain.model.Journal

sealed interface JournalCreationState {
    sealed interface Intent {
        data object NavigateUp : Intent
    }

    data object Loading : JournalCreationState

    data class Default(
        val journal: Journal = Journal.emptyJournal,
        val showError: Boolean = false
    ) : JournalCreationState {
        sealed interface Intent : JournalCreationState.Intent {
            data object CreateJournalIntent : Intent
            data class SetJournalTitleIntent(val value: String) : Intent
            data class SetJournalDescriptionIntent(val value: String) : Intent
            data class SetJournalColorIntent(val value: String) : Intent
        }


        val allFieldsFilled: Boolean
            get() {
                return arrayOf(journal.title, journal.color).all { it.isNotBlank() }
            }
    }
}