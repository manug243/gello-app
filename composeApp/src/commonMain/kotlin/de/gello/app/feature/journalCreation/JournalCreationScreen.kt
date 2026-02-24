package de.gello.app.feature.journalCreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.journalCreation.JournalCreationState.Default
import de.gello.app.feature.journalCreation.JournalCreationState.Default.Intent
import de.gello.app.feature.journalCreation.ui.JournalCreationPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigation
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.screen_title_create_journal
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun JournalCreationScreen(viewmodel: JournalCreationViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    JournalCreationScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
fun JournalCreationScreenImpl(
    state: JournalCreationState,
    executeIntent: (JournalCreationState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        when (state) {
            is JournalCreationState.Loading -> CoveringProgressIndicator()
            is Default -> DefaultsPage(
                snackBarHost = snackBarHost,
                onNavigateBack = { executeIntent(JournalCreationState.Intent.NavigateUp) },
                state = state,
                executeIntent = executeIntent
            )
        }
    }

}

@Composable
private fun DefaultsPage(
    snackBarHost: @Composable () -> Unit = {},
    onNavigateBack: () -> Unit,
    state: Default,
    executeIntent: (JournalCreationState.Intent) -> Unit
) {

    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigation(
                title = stringResource(Res.string.screen_title_create_journal),
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        JournalCreationPage(
            state = state,
            onTitleChanged = {
                executeIntent(Intent.SetJournalTitleIntent(it))
            },
            onDescChanged = {
                executeIntent(Intent.SetJournalDescriptionIntent(it))
            },
            onColorChanged = {
                executeIntent(Intent.SetJournalColorIntent(it))
            },
            onCreateClick = {
                executeIntent(Intent.CreateJournalIntent)
            }
        )
    }
}