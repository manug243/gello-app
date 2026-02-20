package de.gello.app.feature.auth.login

sealed interface LoginState {
    sealed interface Intent {
        // future navigation
        data object ToForgetPassword : Intent
        data object ToRegistration : Intent
    }

    data object Loading : LoginState

    data class Default(
        val username: String = "",
        val password: String = "",
        val showError: Boolean = false
    ) : LoginState {
        sealed interface Intent : LoginState.Intent {
            data class SetUsername(val value: String) : Intent
            data class SetPassword(val value: String) : Intent
            data object LoginButton : Intent
        }
    }
}