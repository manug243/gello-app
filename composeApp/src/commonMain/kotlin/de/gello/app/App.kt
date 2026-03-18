package de.gello.app

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import de.gello.app.di.appModule
import de.gello.app.navigation.AppNavigation
import de.gello.data.di.dataModule
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.theme.AppTheme
import io.github.vinceglb.filekit.coil.addPlatformFileSupport
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GelloApp() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                addPlatformFileSupport()
            }
            .build()
    }

    KoinMultiplatformApplication(
        config = koinConfiguration {
            modules(appModule + dataModule)
        }
    ) {
        AppTheme {
            Surface {
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