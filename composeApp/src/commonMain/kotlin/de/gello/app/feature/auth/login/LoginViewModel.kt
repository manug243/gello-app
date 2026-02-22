package de.gello.app.feature.auth.login

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import de.gello.app.event.UIEvent
import de.gello.app.navigation.AuthScreen
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
                dispatchNavigationEvent(
                    NavigationEvent.NavigateTo(AuthScreen.Register)
                )

            is LoginState.Default.Intent.SetUsername -> reduceState<LoginState.Default> {
                copy(username = intent.value, showError = false)
            }

            is LoginState.Default.Intent.SetPassword -> reduceState<LoginState.Default> {
                copy(password = intent.value, showError = false)
            }

            is LoginState.Default.Intent.LoginButton -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleLoginButton
            )
        }

    private fun handleLoginButton(
        state: LoginState.Default,
        intent: LoginState.Default.Intent.LoginButton
    ) {
        viewModelScope.launch {
            loginUseCase(
                LoginUseCase.Params(
                    username = state.username,
                    password = state.password
                )
            ).collectOutcome(
                progressDelay = 1000,
                onProgress = { setState(LoginState.Loading) },
                onFailure = {
                    setState(state.copy(showError = true))
                    showSnackbar(it.message)
                }
            )
        }
    }
}