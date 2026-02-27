package de.gello.app.feature.auth.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import de.gello.app.feature.auth.registration.RegistrationState
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PasswordTextField
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.button_register
import gello.composeapp.generated.resources.placeholder_firstname
import gello.composeapp.generated.resources.placeholder_password
import gello.composeapp.generated.resources.placeholder_repeat_password
import gello.composeapp.generated.resources.placeholder_surname
import gello.composeapp.generated.resources.placeholder_username
import gello.composeapp.generated.resources.registration_header_subtitle
import gello.composeapp.generated.resources.registration_header_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegistrationPage(
    state: RegistrationState.Default,
    onFirstnameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onClickRegister: () -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        Header()

        RegisterForm(
            state = state,
            onFirstnameChange = onFirstnameChange,
            onSurnameChange = onSurnameChange,
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange,
            onRepeatPasswordChange = onRepeatPasswordChange,
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            title = stringResource(Res.string.button_register),
            isEnabled = state.allFieldsFilled,
            onClick = onClickRegister
        )
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = Spacing.Small),
            text = stringResource(Res.string.registration_header_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(Res.string.registration_header_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RegisterForm(
    state: RegistrationState.Default,
    onFirstnameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit
    ) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        TextField(
            value = state.firstname,
            placeholder = stringResource(Res.string.placeholder_firstname),
            onValueChanged = onFirstnameChange,
            contentType = ContentType.PersonFirstName,
            isError = state.showError,
            imeAction = ImeAction.Next
        )

        TextField(
            value = state.surname,
            placeholder = stringResource(Res.string.placeholder_surname),
            onValueChanged = onSurnameChange,
            contentType = ContentType.PersonLastName,
            isError = state.showError,
            imeAction = ImeAction.Next
        )

        TextField(
            value = state.username,
            placeholder = stringResource(Res.string.placeholder_username),
            onValueChanged = onUsernameChange,
            contentType = ContentType.NewUsername,
            isError = state.showError,
            imeAction = ImeAction.Next
        )

        PasswordTextField(
            value = state.password,
            placeholder = stringResource(Res.string.placeholder_password),
            onPasswordChange = onPasswordChange,
            contentType = ContentType.NewPassword,
            imeAction = ImeAction.Next,
            isError = state.showError || state.passwordsMismatch,
            supportingText = if (state.passwordsMismatch) "Passwords are not identical." else null
        )

        PasswordTextField(
            value = state.repeatedPassword,
            placeholder = stringResource(Res.string.placeholder_repeat_password),
            onPasswordChange = onRepeatPasswordChange,
            contentType = ContentType.NewPassword,
            imeAction = ImeAction.Done,
            isError = state.showError || state.passwordsMismatch,
            supportingText = if (state.passwordsMismatch) "Passwords are not identical." else null
        )
    }
}