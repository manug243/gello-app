package de.gello.app.feature.example

sealed interface ExampleState {
    sealed interface Intent {
        // possible navigation
    }

    data object Loading : ExampleState

    data class Default(
        val showError: Boolean = false
    ) : ExampleState {
        sealed interface Intent : ExampleState {
            // possible text field, buttons etc
        }
    }
}