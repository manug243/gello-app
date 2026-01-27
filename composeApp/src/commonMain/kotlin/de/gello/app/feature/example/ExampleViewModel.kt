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
            is ExampleState.Intent.Logout -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleLogoutButton
            )

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

    private fun handleLogoutButton(
        state: ExampleState,
        intent: ExampleState.Intent.Logout
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