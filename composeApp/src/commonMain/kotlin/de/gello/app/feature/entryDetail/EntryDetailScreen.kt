package de.gello.app.feature.entryDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.entryDetail.ui.EntryDetailPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigation
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
                    }
                )
        }
    }
}

@Composable
private fun DefaultsPage(
    snackBarHost: @Composable () -> Unit = {},
    state: EntryDetailState.Default,
    onNavigateBack: () -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigation(
                title = state.entry.name,
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        EntryDetailPage(

        )
    }
}