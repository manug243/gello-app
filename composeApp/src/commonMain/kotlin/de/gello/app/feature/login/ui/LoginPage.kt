package de.gello.app.feature.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import de.gello.app.feature.login.LoginState
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PasswordTextFieldWithForgotPassword
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.appicon
import gello.composeapp.generated.resources.button_login
import gello.composeapp.generated.resources.placeholder_email
import gello.composeapp.generated.resources.placeholder_password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginPage(
    state: LoginState.Default,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickRegister: () -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        Header()

        LoginForm(
            state = state,
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
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.appicon),
            contentDescription = null,
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Welcome to Gello",
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
private fun LoginForm(
    state: LoginState.Default,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickForgetPassword: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
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