package de.gello.app.navigation

import kotlinx.serialization.Serializable

@Serializable
interface AuthScreen : Screen {
    @Serializable
    data object Graph : AuthScreen

    @Serializable
    data object Login : AuthScreen
}