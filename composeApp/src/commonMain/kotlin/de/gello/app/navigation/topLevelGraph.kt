package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.gello.app.feature.settings.SettingsScreen
import de.gello.app.feature.settings.SettingsViewModel
import de.gello.app.feature.overview.OverviewScreen
import de.gello.app.feature.overview.OverviewViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.topLevelGraph() {
    // here we are going to register the actual top level screens, like actual tab screens ("first screens") maybe Dairy-tab
    // example:
    // composableWithTransition<TopLevelScreen.Diary> {
    //        DairyScreen()
    //    }

    composable<TopLevelScreen.Settings> {
        val viewModel = koinViewModel<SettingsViewModel>()
        SettingsScreen(viewmodel = viewModel)
    }

    composable<TopLevelScreen.Overview> {
        val viewModel = koinViewModel<OverviewViewModel>()
        OverviewScreen(viewmodel = viewModel)
    }
}