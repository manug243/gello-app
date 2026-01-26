package de.gello.app.feature.example

import androidx.lifecycle.viewModelScope
import de.gello.app.event.UIEvent
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

// need to register in di
class ExampleViewModel(
    private val logoutUseCase: LogoutUseCase
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

            is ExampleState.Default.Intent.LogoutButton -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleLogoutButton
            )
        }
    }

    private fun handleLogoutButton(
        state: ExampleState.Default,
        intent: ExampleState.Default.Intent.LogoutButton
    ) {
        viewModelScope.launch {
            logoutUseCase
        }
    }

    private fun handleSetTextFieldIntent(
        state: ExampleState.Default,
        intent: ExampleState.Default.Intent.SetExampleTextField
    ) {
        setState(state.copy(textField = intent.value))
    }
}