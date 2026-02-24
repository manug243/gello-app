package de.gello.app.feature.auth.registration

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel<RegistrationState, RegistrationState.Intent>(
    initialState = RegistrationState.Default(),
    useEventBus = false
) {
    override fun executeIntent(intent: RegistrationState.Intent) =
        when (intent) {
            is RegistrationState.Intent.NavigateUp -> dispatchNavigationEvent(NavigationEvent.NavigateUp)

            is RegistrationState.Default.Intent.RegisterButton -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleRegistrationIntent
            )

            is RegistrationState.Default.Intent.SetUsername -> reduceState<RegistrationState.Default> {
                copy(username = intent.value, showError = false)
            }

            is RegistrationState.Default.Intent.SetPassword -> reduceState<RegistrationState.Default> {
                copy(password = intent.value, showError = false)
            }
        }

    private fun handleRegistrationIntent(
        state: RegistrationState.Default,
        intent: RegistrationState.Default.Intent.RegisterButton
    ) {
        viewModelScope.launch {
            registerUserUseCase(
                params = RegisterUserUseCase.Params(
                    username = state.username,
                    password = state.password
                )
            ).collectOutcome(
                onProgress = { setState(RegistrationState.Loading) },
                onFailure = {
                    setState(state.copy(showError = true))
                    showSnackbar(it.message)
                },
                onSuccess = { setState(RegistrationState.Complete) }
            )
        }
    }
}