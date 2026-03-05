package de.gello.app.feature.journalDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.journalDetail.ui.JournalDetailPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.FAB
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigationAndActionButton
import kotlinx.coroutines.flow.Flow

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
                    onClickAdd = {
                        executeIntent(JournalDetailState.Default.Intent.AddButton)
                    },
                    onClickDelete = {
                        executeIntent(JournalDetailState.Default.Intent.DeleteButton)
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
    onClickAdd: () -> Unit,
    onClickDelete: () -> Unit,
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
                    IconButton(
                        onClick = onClickDelete
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = true) {
                FAB(
                    onClick = onClickAdd
                )
            }
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