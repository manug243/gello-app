package de.gello.app.feature.overview

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.overview.ui.OverviewPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import kotlinx.coroutines.flow.Flow

@Composable
fun OverviewScreen(viewmodel: OverviewViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    OverviewScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun OverviewScreenImpl(
    state: OverviewState,
    executeIntent: (OverviewState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        ScreenScaffold(
            snackbarHost = snackBarHost
        ) {
            when (state) {
                is OverviewState.Loading -> CoveringProgressIndicator()
                is OverviewState.Default -> {
                    OverviewPage(
                        state = state
                    )
                }
            }
        }
    }
}