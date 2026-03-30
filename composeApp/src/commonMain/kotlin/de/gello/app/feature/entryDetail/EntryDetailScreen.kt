package de.gello.app.feature.entryDetail

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.entryDetail.ui.EntryDetailPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigationAndActionButton
import kotlinx.coroutines.flow.Flow

@Composable
fun EntryDetailScreen(viewmodel: EntryDetailViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()


    EntryDetailScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun EntryDetailScreenImpl(
    state: EntryDetailState,
    executeIntent: (EntryDetailState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        when (state) {
            is EntryDetailState.Loading -> CoveringProgressIndicator()
            is EntryDetailState.Default ->
                DefaultsPage(
                    snackBarHost = snackBarHost,
                    state = state,
                    onNavigateBack = {
                        executeIntent(EntryDetailState.Intent.NavigateUp)
                    },
                    onClickButton = {
                        executeIntent(EntryDetailState.Default.Intent.DeleteButton)
                    }
                )
        }
    }
}

@Composable
private fun DefaultsPage(
    snackBarHost: @Composable () -> Unit = {},
    state: EntryDetailState.Default,
    onNavigateBack: () -> Unit,
    onClickButton: () -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigationAndActionButton(
                title = state.entry.name,
                onNavigateBack = onNavigateBack,
                actionButtonContent = {
                    IconButton(
                        onClick = onClickButton
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        EntryDetailPage(state = state)
    }
}