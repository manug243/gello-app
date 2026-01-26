package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.login.LoginScreen
import de.gello.app.feature.login.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authGraph() {

    composableWithTransition<AuthScreen.Login> {
        val viewModel = koinViewModel<LoginViewModel>()
        LoginScreen(viewmodel = viewModel)
    }
}