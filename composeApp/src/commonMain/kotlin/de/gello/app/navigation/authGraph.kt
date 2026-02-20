package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.auth.login.LoginScreen
import de.gello.app.feature.auth.login.LoginViewModel
import de.gello.app.feature.auth.registration.RegistrationScreen
import de.gello.app.feature.auth.registration.RegistrationViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authGraph() {

    composable<AuthScreen.Login> {
        val viewModel = koinViewModel<LoginViewModel>()
        LoginScreen(viewmodel = viewModel)
    }

    composableWithTransition<AuthScreen.Register> {
        val viewModel = koinViewModel<RegistrationViewModel>()
        RegistrationScreen(viewmodel = viewModel)
    }
}