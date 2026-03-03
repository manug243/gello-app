package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.settings.SettingsScreen
import de.gello.app.feature.settings.SettingsViewModel
import de.gello.app.feature.journalCreation.JournalCreationScreen
import de.gello.app.feature.journalCreation.JournalCreationViewModel
import org.koin.compose.koinInject

fun NavGraphBuilder.appGraph() {

    composableWithTransition<Screen.Example> {
        val viewModel = koinInject<SettingsViewModel>()
        SettingsScreen(viewmodel = viewModel)
    }

    composableWithTransition<Screen.JournalCreation> {
        val viewModel = koinInject<JournalCreationViewModel>()
        JournalCreationScreen(viewmodel = viewModel)
    }
}