package de.gello.app.feature.overview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.feature.overview.ui.OverviewPage
import de.gello.designsystem.component.CoveringProgressIndicator

@Composable
fun OverviewScreen(viewmodel: OverviewViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.refreshJournals()
    }

    OverviewScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewScreenImpl(
    state: OverviewState,
    executeIntent: (OverviewState.Intent) -> Unit
) {
    when (state) {
        is OverviewState.Loading -> CoveringProgressIndicator()
        is OverviewState.Default -> {
            OverviewPage(
                state = state,
                onClick = { id ->
                    executeIntent(OverviewState.Intent.NavigateToOneJournal(id = id))
                }
            )
        }
    }
}