package de.gello.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val appViewModel = koinViewModel<AppViewModel>()
                val state by appViewModel.collectStateFlow().collectAsState()

                AppContent(
                    state = state
                )
            }
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