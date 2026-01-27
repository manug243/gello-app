package de.gello.app.feature.login

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skash.forge.outcome.collectOutcome
import de.gello.app.event.UIEvent
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginState.Intent>(
    initialState = LoginState.Default(),
    useEventBus = false
) {

    override fun executeIntent(intent: LoginState.Intent) =
        when (intent) {
            is LoginState.Intent.ToForgetPassword ->
                sendUIEvent(
                    UIEvent.Snackbar("Not implemented yet")
                )

            is LoginState.Intent.ToRegistration ->
                sendUIEvent(
                    UIEvent.Snackbar("Not implemented yet")
                )

            is LoginState.Default.Intent.SetEmail ->
                handleIntent<_, _>(
                    intent = intent,
                    handler = ::handleSetEmailIntent
                )

            is LoginState.Default.Intent.SetPassword ->
                handleIntent<_, _>(
                    intent = intent,
                    handler = ::handleSetPasswordIntent
                )

            is LoginState.Default.Intent.LoginButton -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleLoginButton
            )
        }

    private fun handleSetEmailIntent(
        state: LoginState.Default,
        intent: LoginState.Default.Intent.SetEmail
    ) {
        setState(state.copy(email = intent.value))
    }

    private fun handleSetPasswordIntent(
        state: LoginState.Default,
        intent: LoginState.Default.Intent.SetPassword
    ) {
        setState(state.copy(password = intent.value))
    }

    private fun handleLoginButton(
        state: LoginState.Default,
        intent: LoginState.Default.Intent.LoginButton
    ) {
        viewModelScope.launch {
            loginUseCase(
                LoginUseCase.Params(
                    email = state.email,
                    password = state.password
                )
            ).collectOutcome(
                progressDelay = 1000,
                onProgress = { setState(LoginState.Loading) },
                onFailure = {
                    setState(state)
                    showSnackbar(it.message)
                }
            )
        }
    }
}