package de.gello.app.feature.auth.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.auth.registration.RegistrationState.Default
import de.gello.app.feature.auth.registration.ui.RegistrationCompletePage
import de.gello.app.feature.auth.registration.ui.RegistrationPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigation
import kotlinx.coroutines.flow.Flow

@Composable
fun RegistrationScreen(viewmodel: RegistrationViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    RegistrationScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
private fun RegistrationScreenImpl(
    state: RegistrationState,
    executeIntent: (RegistrationState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->

        when (state) {
            is RegistrationState.Loading -> CoveringProgressIndicator()

            is RegistrationState.Complete -> CompletePage(executeIntent = executeIntent)

            is Default -> DefaultsPage(
                snackBarHost = snackBarHost,
                onNavigateBack = {
                    executeIntent(RegistrationState.Intent.NavigateUp)
                },
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
    executeIntent: (RegistrationState.Intent) -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigation(
                title = "",
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        RegistrationPage(
            state = state,
            onUsernameChange = {
                executeIntent(Default.Intent.SetUsername(it))
            },
            onPasswordChange = {
                executeIntent(Default.Intent.SetPassword(it))
            },
            onClickRegister = {
                executeIntent(Default.Intent.RegisterButton)
            }
        )
    }
}

@Composable
private fun CompletePage(
    executeIntent: (RegistrationState.Intent) -> Unit
) {
    ScreenScaffold {
        RegistrationCompletePage {
            executeIntent(RegistrationState.Intent.NavigateUp)
        }
    }
}

