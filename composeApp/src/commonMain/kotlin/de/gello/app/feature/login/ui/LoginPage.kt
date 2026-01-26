package de.gello.app.feature.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import de.gello.app.feature.login.LoginState
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PasswordTextFieldWithForgotPassword
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.button_login
import gello.composeapp.generated.resources.placeholder_email
import gello.composeapp.generated.resources.placeholder_password
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginPage(
    state: LoginState.Default,
    executeIntent: (LoginState.Intent) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickRegister: () -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        LoginForm(
            state = state,
            executeIntent = executeIntent,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onClickForgetPassword = onClickForgetPassword
        )

        Spacer(Modifier.weight(1f))

        PrimaryButton(
            title = stringResource(Res.string.button_login),
            isEnabled = true,
            onClick = onLoginClick
        )
    }
}

@Composable
private fun LoginForm(
    state: LoginState.Default,
    executeIntent: (LoginState.Intent) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickForgetPassword: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = Spacing.Medium)
    ) {
        TextField(
            value = state.email,
            placeholder = stringResource(Res.string.placeholder_email),
            onValueChanged = onEmailChange
        )

        PasswordTextFieldWithForgotPassword(
            value = state.password,
            placeholder = stringResource(Res.string.placeholder_password),
            onPasswordChange = onPasswordChange,
            imeAction = ImeAction.Done,
            onForgotPasswordClick = onClickForgetPassword
        )
    }
}