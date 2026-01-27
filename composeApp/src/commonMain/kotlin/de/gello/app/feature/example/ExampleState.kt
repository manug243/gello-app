package de.gello.app.feature.example

sealed interface ExampleState {
    sealed interface Intent {
        data object Logout : Default.Intent
    }

    data object Loading : ExampleState

    data class Default(
        val textField: String = "",
        val showError: Boolean = false
    ) : ExampleState {
        sealed interface Intent : ExampleState.Intent {
            // possible text field, buttons etc
            data object ExampleButton : Intent
            data class SetExampleTextField(val value: String) : Intent
        }
    }
}