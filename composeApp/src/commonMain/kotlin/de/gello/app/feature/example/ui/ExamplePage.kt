package de.gello.app.feature.example.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import de.gello.app.feature.example.ExampleState
import de.gello.app.feature.login.LoginState
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing

@Composable
internal fun ExamplePage(
    state: ExampleState.Default,
    onTextFieldChange: (String) -> Unit,
    onExampleButtonClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    // design of actual screen, we need to consider if we can use page, because of desktop
    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Welcome to Gello's example screen"
        )

        TextField(
            value = state.textField,
            placeholder = "Enter text",
            onValueChanged = onTextFieldChange,
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))

        PrimaryButton(
            title = "Send snackbar message",
            isEnabled = true,
            onClick = onExampleButtonClick
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            title = "Logout",
            isEnabled = true,
            onClick = onLogoutClick
        )
    }
}