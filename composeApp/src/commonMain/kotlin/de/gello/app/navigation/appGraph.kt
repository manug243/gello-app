package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.example.ExampleScreen
import de.gello.app.feature.example.ExampleViewModel
import de.gello.app.feature.journalCreation.JournalCreationScreen
import de.gello.app.feature.journalCreation.JournalCreationViewModel
import de.gello.app.feature.overview.OverviewScreen
import de.gello.app.feature.overview.OverviewViewModel
import org.koin.compose.koinInject

fun NavGraphBuilder.appGraph() {

    composableWithTransition<Screen.Example> {
        val viewModel = koinInject<ExampleViewModel>()
        ExampleScreen(viewmodel = viewModel)
    }

    composableWithTransition<Screen.JournalCreation> {
        val viewModel = koinInject<JournalCreationViewModel>()
        JournalCreationScreen(viewmodel = viewModel)
    }
}