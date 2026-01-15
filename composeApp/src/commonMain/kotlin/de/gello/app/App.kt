package de.gello.app

import androidx.compose.runtime.*
import de.gello.app.di.appModule
import de.gello.app.navigation.AppNavigation
import de.gello.data.di.dataModule
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.theme.AppTheme
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GelloApp() {
    KoinMultiplatformApplication(
        config = koinConfiguration {
            modules(appModule + dataModule)
        }
    ) {
        AppTheme {
            val appViewModel = koinViewModel<AppViewModel>()
            val state by appViewModel.collectStateFlow().collectAsState()

            AppContent(
                state = state
            )
        }
    }
}

@Composable
private fun AppContent(state: AppState) {
    when (state) {
        is AppState.Loaded -> AppNavigation(isLoggedIn = state.isLoggedIn)
        is AppState.Loading -> CoveringProgressIndicator()
    }
}