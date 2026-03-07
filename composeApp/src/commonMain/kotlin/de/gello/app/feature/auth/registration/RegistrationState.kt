package de.gello.app.feature.auth.registration

sealed interface RegistrationState {
    sealed interface Intent {
        data object NavigateUp : Intent
    }

    data object Loading : RegistrationState

    data object Complete : RegistrationState

    data class Default(
        val firstname: String = "",
        val lastname: String = "",
        val username: String = "",
        val password: String = "",
        val repeatedPassword: String = "",
        val showError: Boolean = false
    ) : RegistrationState {
        sealed interface Intent : RegistrationState.Intent {
            data class SetFirstname(val value: String) : Intent
            data class SetSurname(val value: String) : Intent
            data class SetUsername(val value: String) : Intent
            data class SetPassword(val value: String) : Intent
            data class SetRepeatPassword(val value: String) : Intent
            data object RegisterButton : Intent
        }

        val passwordsMismatch: Boolean
            get() {
                return password.isNotBlank() &&
                        repeatedPassword.isNotBlank() &&
                        password != repeatedPassword
            }

        val allFieldsFilled: Boolean
            get() {
                val allFilled = arrayOf(firstname, lastname, username, password, repeatedPassword)
                    .all { it.isNotBlank() }

                return allFilled && !passwordsMismatch
            }
    }
}