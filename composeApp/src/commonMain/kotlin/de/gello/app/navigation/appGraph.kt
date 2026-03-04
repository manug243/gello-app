package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.journalCreation.JournalCreationScreen
import de.gello.app.feature.journalCreation.JournalCreationViewModel
import de.gello.app.feature.journalDetail.JournalDetailScreen
import de.gello.app.feature.settings.SettingsScreen
import de.gello.app.feature.settings.SettingsViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.appGraph() {

    composableWithTransition<Screen.Settings> {
        val viewModel = koinInject<SettingsViewModel>()
        SettingsScreen(viewmodel = viewModel)
    }

    composableWithTransition<Screen.JournalCreation> {
        val viewModel = koinInject<JournalCreationViewModel>()
        JournalCreationScreen(viewmodel = viewModel)
    }

    composableWithTransition<Screen.JournalDetails> {
        val route = it.toRoute<Screen.JournalDetails>()

        JournalDetailScreen(
            viewmodel = koinViewModel(parameters = { parametersOf(route.id) })
        )
    }
}