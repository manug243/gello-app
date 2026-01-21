package de.gello.app.feature.login

sealed interface LoginState {
    sealed interface Intent {
        // future navigation
        data object ToForgetPassword : Intent
        data object ToRegistration : Intent
    }

    data object Loading : LoginState

    data class Default(
        val email: String = "",
        val password: String = "",
        val showError: Boolean = false
    ) : LoginState {
        sealed interface Intent : LoginState.Intent {
            data class SetEmail(val value: String) : Intent
            data class SetPassword(val value: String) : Intent
            data object LoginButton : Intent
        }
    }
}