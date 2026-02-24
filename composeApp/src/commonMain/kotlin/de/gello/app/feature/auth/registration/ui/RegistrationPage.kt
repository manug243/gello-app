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
import gello.composeapp.generated.resources.placeholder_password
import gello.composeapp.generated.resources.placeholder_username
import gello.composeapp.generated.resources.registration_header_subtitle
import gello.composeapp.generated.resources.registration_header_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegistrationPage(
    state: RegistrationState.Default,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickRegister: () -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        Header()

        Spacer(modifier = Modifier.height(Spacing.Medium))

        TextField(
            value = state.username,
            placeholder = stringResource(Res.string.placeholder_username),
            onValueChanged = onUsernameChange,
            contentType = ContentType.NewUsername,
            isError = state.showError
        )

        PasswordTextField(
            value = state.password,
            placeholder = stringResource(Res.string.placeholder_password),
            onPasswordChange = onPasswordChange,
            contentType = ContentType.NewPassword,
            imeAction = ImeAction.Done,
            isError = state.showError
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))

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