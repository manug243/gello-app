package de.gello.app.feature.example

import de.gello.app.event.UIEvent
import de.gello.app.util.BaseViewModel

// need to register in di
class ExampleViewModel(
    // usecase
) : BaseViewModel<ExampleState, ExampleState.Intent>(
    initialState = ExampleState.Default()
) {
    override fun executeIntent(intent: ExampleState.Intent) {
        when (intent) {
            // need to list all intents
            is ExampleState.Default.Intent.ExampleButton ->
                sendUIEvent(
                    UIEvent.Snackbar("Example action")
                )

            is ExampleState.Default.Intent.SetExampleTextField -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleSetTextFieldIntent
            )
        }
    }

    private fun handleExampleButton(
        state: ExampleState.Default,
        intent: ExampleState.Default.Intent.ExampleButton
    ) {
        showSnackbar(state.textField)
    }

    private fun handleSetTextFieldIntent(
        state: ExampleState.Default,
        intent: ExampleState.Default.Intent.SetExampleTextField
    ) {
        setState(state.copy(textField = intent.value))
    }
}