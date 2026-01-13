package de.gello.app.event

import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.flow.Flow

@Composable
fun UIEventHandler(
    uiEvents: Flow<UIEvent>,
    content: @Composable (snackbarHost: @Composable () -> Unit) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarHost: @Composable () -> Unit = {
        SnackbarHost(hostState = snackbarHostState)
    }

    EventHandler(
        events = uiEvents,
        onEvent = {
            when (it) {
                is UIEvent.Snackbar -> snackbarHostState.showSnackbar(message = it.message)
            }
        }
    )

    content(snackbarHost)
}

@Composable
fun <T : Any> EventHandler(
    events: Flow<T>,
    onEvent: suspend (event: T) -> Unit,
) {
    val latestOnEvent by rememberUpdatedState(onEvent)

    LaunchedEffect(events) {
        events.collect { event ->
            latestOnEvent(event)
        }
    }
}