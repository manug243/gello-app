package de.gello.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClickableTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onClick: () -> Unit,
    trailingIcon: @Composable (() -> Unit) = {
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
    },
    supportingText: String? = null
) {
    Box(
        modifier = Modifier
            .clickable { onClick() },
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            placeholder = { Text(text = placeholder) },
            label = { Text(text = placeholder) },
            enabled = false,
            readOnly = true,
            trailingIcon = trailingIcon,
            supportingText = supportingText?.let { { Text(text = it) } },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            singleLine = true,
        )
    }
}