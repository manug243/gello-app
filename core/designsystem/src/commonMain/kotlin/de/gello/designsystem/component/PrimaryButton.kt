package de.gello.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.designsystem.theme.Spacing

@Composable
fun PrimaryButton(
    title: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.Medium),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            text = title
        )
    }
}