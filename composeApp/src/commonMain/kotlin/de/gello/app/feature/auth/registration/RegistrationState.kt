package de.gello.app.feature.auth.registration

sealed interface RegistrationState {
    sealed interface Intent {
        data object NavigateUp : Intent
    }

    data object Loading : RegistrationState

    data object Complete : RegistrationState

    data class Default(
        val username: String = "",
        val password: String = "",
        val showError: Boolean = false
    ) : RegistrationState {
        sealed interface Intent : RegistrationState.Intent {
            data class SetUsername(val value: String) : Intent
            data class SetPassword(val value: String) : Intent
            data object RegisterButton : Intent
        }

        val allFieldsFilled: Boolean
            get() {
                return arrayOf(username, password).all { it.isNotBlank() }
            }
    }
}