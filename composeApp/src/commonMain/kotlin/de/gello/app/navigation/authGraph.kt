package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.gello.app.feature.auth.login.LoginScreen
import de.gello.app.feature.auth.login.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authGraph() {

    composable<AuthScreen.Login> {
        val viewModel = koinViewModel<LoginViewModel>()
        LoginScreen(viewmodel = viewModel)
    }
}