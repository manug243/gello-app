package de.gello.app.feature.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.example.ui.ExamplePage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExampleScreen(viewmodel: ExampleViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    ExampleScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun ExampleScreenImpl(
    state: ExampleState,
    executeIntent: (ExampleState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        ScreenScaffold(
            snackbarHost = snackBarHost
        ) {
            when (state) {
                is ExampleState.Loading -> CoveringProgressIndicator()
                is ExampleState.Default ->
                    ExamplePage(
                        state = state,
                        onTextFieldChange = {
                            executeIntent(ExampleState.Default.Intent.SetExampleTextField(it))
                        },
                        onExampleButtonClick = {
                            executeIntent(ExampleState.Default.Intent.ExampleButton)
                        },
                        onLogoutClick = {
                            executeIntent(ExampleState.Intent.Logout)
                        }
                    )
            }
        }
    }
}