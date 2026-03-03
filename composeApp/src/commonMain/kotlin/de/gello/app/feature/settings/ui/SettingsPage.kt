package de.gello.app.feature.settings.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PrimaryButton
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.button_logout
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsPage(
    onLogoutClick: () -> Unit
) {
    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            title = stringResource(Res.string.button_logout),
            isEnabled = true,
            onClick = onLogoutClick
        )
    }
}