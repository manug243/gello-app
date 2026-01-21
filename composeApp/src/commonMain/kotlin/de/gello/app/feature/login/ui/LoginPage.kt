package de.gello.app.feature.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.app.feature.login.LoginState
import de.gello.designsystem.component.Page
import de.gello.designsystem.theme.Spacing

@Composable
internal fun LoginPage(
    state: LoginState.Default,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onClickForgetPassword: () -> Unit,
    onClickRegister: () -> Unit,
    executeIntent: (LoginState.Intent) -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

    }
}

@Composable
private fun LoginForm(
    state: LoginState.Default,
    executeIntent: (LoginState.Intent) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = Spacing.Medium)
    ) {
        
    }
}