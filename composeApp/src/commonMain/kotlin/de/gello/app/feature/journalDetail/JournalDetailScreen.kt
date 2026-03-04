package de.gello.app.feature.journalDetail

import androidx.compose.runtime.Composable
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
            is JournalDetailState.Default -> {
                DefaultsPage(
                    snackBarHost = snackBarHost,
                    state = state,
                    onNavigateBack = {
                        executeIntent(JournalDetailState.Intent.NavigateUp)
                    },
                    onClickActionButton = {
                        executeIntent(JournalDetailState.Default.Intent.AddButton)
                    }
                )
            }
        }
    }
}

@Composable
private fun DefaultsPage(
    snackBarHost: @Composable () -> Unit = {},
    state: JournalDetailState.Default,
    onNavigateBack: () -> Unit,
    onClickActionButton: () -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigationAndActionButton(
                title = state.journal.title,
                onNavigateBack = onNavigateBack,
                onClickActionButton = onClickActionButton,
                buttonTitle = stringResource(Res.string.button_add)
            )
        }
    ) {
        JournalDetailPage(
            state = state
        )
    }
}