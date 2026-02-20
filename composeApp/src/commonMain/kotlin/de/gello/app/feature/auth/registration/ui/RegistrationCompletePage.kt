package de.gello.app.feature.auth.registration.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.gello.designsystem.component.Page
import de.gello.designsystem.theme.Spacing
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.registration_success_subtitle
import gello.composeapp.generated.resources.registration_success_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegistrationCompletePage(
    onConfirm: () -> Unit
) {
    Page(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onConfirm
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null,
            tint = Color.Cyan,
            modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))

        Text(
            text = stringResource(Res.string.registration_success_title),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(Res.string.registration_success_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}