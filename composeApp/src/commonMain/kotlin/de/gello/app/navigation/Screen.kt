package de.gello.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Graph : Screen

    @Serializable
    data object Settings : Screen

    @Serializable
    data object JournalCreation : Screen

    @Serializable
    data class JournalDetails(val id: Int) : Screen

    @Serializable
    data class EntryDetails(val journalId: Int, val entryId: Int) : Screen

    @Serializable
    data object EntryCreation : Screen
}