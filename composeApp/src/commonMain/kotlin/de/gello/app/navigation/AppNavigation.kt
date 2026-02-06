package de.gello.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.skash.forge.navigation.NavigationDispatcher
import com.skash.forge.navigation.nav2.CollectNavigationEvents
import com.skash.forge.navigation.nav2.DefaultNavHost
import de.gello.app.AppViewModel
import de.gello.app.MainScreen
import org.koin.compose.koinInject

@Composable
fun AppNavigation(isLoggedIn: Boolean) {
    val rootNavController = rememberNavController()
    val dispatcher = koinInject<NavigationDispatcher>()

    rootNavController.CollectNavigationEvents(dispatcher)

    DefaultNavHost(
        navController = rootNavController,
        startDestination = if (isLoggedIn) TopLevelScreen.Graph else AuthScreen.Graph,
        builder = {
            navigation<AuthScreen.Graph>(
                startDestination = AuthScreen.Login
            ) {
                authGraph()
            }

            navigation<Screen.Graph>(
                startDestination = Screen.Example
            ) {
                appGraph()
            }

            composable<TopLevelScreen.Graph> {
                val viewModel = koinInject<AppViewModel>()
                MainScreen(viewmodel = viewModel)
            }
        }
    )
}