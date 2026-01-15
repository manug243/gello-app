package de.gello.app.feature.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.feature.example.view.ExamplePage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExampleScreen() {
    val viewmodel = koinViewModel<ExampleViewModel>()
    val state by viewmodel.collectStateFlow().collectAsState()

    ExampleScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent
    )
}

@Composable
private fun ExampleScreenImpl(
    state: ExampleState,
    executeIntent: (ExampleState.Intent) -> Unit
) {

    ScreenScaffold(
        // topappbar, uievents
    ) {
        when (state) {
            is ExampleState.Loading -> CoveringProgressIndicator()
            is ExampleState.Default ->
                ExamplePage(
                    executeIntent = executeIntent
                )
        }
    }
}