package de.gello.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonNavigationToolbar(
    onCancelClick: () -> Unit,
    onNextClick: () -> Unit,
    isNextEnabled: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalFloatingToolbar(
            expanded = true,
            colors = FloatingToolbarDefaults.standardFloatingToolbarColors()
        ) {

            TextButton(
                onClick = onCancelClick
            ) {
                Text("Cancel")
            }

            TextButton(
                onClick = onNextClick,
                enabled = isNextEnabled
            ) {
                Text("Next")
            }
        }
    }
}