package de.gello.app.feature.example

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
            else -> {}
        }
    }
}