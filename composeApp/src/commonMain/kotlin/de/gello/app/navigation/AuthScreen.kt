package de.gello.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthScreen : Screen {
    @Serializable
    data object Graph : AuthScreen

    @Serializable
    data object Login : AuthScreen

   @Serializable
    data object Register : AuthScreen
}