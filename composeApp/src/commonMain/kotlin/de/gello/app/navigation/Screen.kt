package de.gello.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Graph : Screen

    @Serializable
    data object Example : Screen

    @Serializable
    data object JournalCreation : Screen
}