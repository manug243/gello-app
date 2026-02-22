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
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewScreenImpl(
    state: OverviewState
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