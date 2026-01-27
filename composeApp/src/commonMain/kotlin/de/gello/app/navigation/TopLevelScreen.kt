package de.gello.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface TopLevelScreen : Screen {
    @Serializable
    data object Graph : TopLevelScreen

    @Serializable
    data object Example : TopLevelScreen

    @Serializable
    data object Overview : TopLevelScreen
}