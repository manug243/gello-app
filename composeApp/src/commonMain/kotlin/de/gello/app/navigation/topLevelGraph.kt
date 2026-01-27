package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.example.ExampleScreen
import de.gello.app.feature.example.ExampleViewModel
import de.gello.app.feature.overview.OverviewScreen
import de.gello.app.feature.overview.OverviewViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.topLevelGraph() {
    // here we are going to register the actual top level screens, like actual tab screens ("first screens") maybe Dairy-tab
    // example:
    // composableWithTransition<TopLevelScreen.Diary> {
    //        DairyScreen()
    //    }

    composable<TopLevelScreen.Example> {
        val viewModel = koinViewModel<ExampleViewModel>()
        ExampleScreen(viewmodel = viewModel)
    }

    composable<TopLevelScreen.Overview> {
        val viewModel = koinViewModel<OverviewViewModel>()
        OverviewScreen(viewmodel = viewModel)
    }
}