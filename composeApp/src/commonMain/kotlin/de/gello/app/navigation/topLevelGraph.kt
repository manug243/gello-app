package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.gello.app.feature.settings.SettingsScreen
import de.gello.app.feature.settings.SettingsViewModel
import de.gello.app.feature.overview.OverviewScreen
import de.gello.app.feature.overview.OverviewViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.topLevelGraph() {

    composable<TopLevelScreen.Settings> {
        val viewModel = koinViewModel<SettingsViewModel>()
        SettingsScreen(viewmodel = viewModel)
    }

    composable<TopLevelScreen.Overview> {
        val viewModel = koinViewModel<OverviewViewModel>()
        OverviewScreen(viewmodel = viewModel)
    }
}