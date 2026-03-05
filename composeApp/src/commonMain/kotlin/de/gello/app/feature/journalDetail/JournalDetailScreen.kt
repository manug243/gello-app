package de.gello.app.feature.journalDetail

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.journalDetail.ui.JournalDetailPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigationAndActionButton
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.button_add
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun JournalDetailScreen(viewmodel: JournalDetailViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.refreshEntries()
    }

    JournalDetailScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun JournalDetailScreenImpl(
    state: JournalDetailState,
    executeIntent: (JournalDetailState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        when (state) {
            is JournalDetailState.Loading -> CoveringProgressIndicator()
            is JournalDetailState.Default ->
                DefaultsPage(
                    snackBarHost = snackBarHost,
                    state = state,
                    onNavigateBack = {
                        executeIntent(JournalDetailState.Intent.NavigateUp)
                    },
                    onClickActionButton = {
                        executeIntent(JournalDetailState.Default.Intent.AddButton)
                    },
                    onEntryClick = { journalId, entryId ->
                        executeIntent(
                            JournalDetailState.Intent.NavigateToEntry(
                                journalId = journalId,
                                entryId = entryId
                            )
                        )
                    },
                    onQueryChanged = {
                        executeIntent(JournalDetailState.Default.Intent.Query(it))
                    }
                )
        }
    }
}

@Composable
private fun DefaultsPage(
    snackBarHost: @Composable () -> Unit = {},
    state: JournalDetailState.Default,
    onNavigateBack: () -> Unit,
    onClickActionButton: () -> Unit,
    onEntryClick: (journalId: Int, entryId: Int) -> Unit,
    onQueryChanged: (String) -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigationAndActionButton(
                title = state.journal.title,
                onNavigateBack = onNavigateBack,
                actionButtonContent = {
                    TextButton(
                        onClick = onClickActionButton
                    ) {
                        Text(text = stringResource(Res.string.button_add))
                    }
                }
            )
        }
    ) {
        JournalDetailPage(
            state = state,
            onEntryClick = { journalId, entryId ->
                onEntryClick(journalId, entryId)
            },
            onQueryChanged = onQueryChanged
        )
    }
}