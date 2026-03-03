package de.gello.app.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.settings.ui.SettingsPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsScreen(viewmodel: SettingsViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    SettingsScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun SettingsScreenImpl(
    state: SettingsState,
    executeIntent: (SettingsState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        ScreenScaffold(
            snackbarHost = snackBarHost
        ) {
            when (state) {
                is SettingsState.Loading -> CoveringProgressIndicator()
                is SettingsState.Default ->
                    SettingsPage(
                        onLogoutClick = {
                            executeIntent(SettingsState.Default.Intent.Logout)
                        }
                    )
            }
        }
    }
}