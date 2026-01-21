package de.gello.app.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.designsystem.component.CoveringProgressIndicator
import kotlinx.coroutines.flow.Flow

@Composable
fun LoginScreen(viewmodel: LoginViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    LoginScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun LoginScreenImpl(
    state: LoginState,
    executeIntent: (LoginState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        when (state) {
            is LoginState.Loading -> CoveringProgressIndicator()
            is LoginState.Default ->
                DefaultsPage(

                )
        }
    }
}
