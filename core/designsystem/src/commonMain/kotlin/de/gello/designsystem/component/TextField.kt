package de.gello.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    prefix: String? = null,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction = ImeAction.Next,
    contentType: ContentType? = null,
    isSingleLine: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    supportingText: String? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .semantics {
                    contentType?.let { this.contentType = it }
                }.then(modifier),
        value = value,
        placeholder = {
            Text(text = placeholder)
        },
        prefix = prefix?.let { { Text(text = it) } },
        label = {
            Text(text = placeholder)
        },
        supportingText = supportingText?.let { { Text(text = it) } },
        readOnly = readOnly,
        enabled = enabled,
        keyboardOptions =
            KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction,
                capitalization = keyboardCapitalization
            ),
        onValueChange = onValueChanged,
        singleLine = isSingleLine,
        isError = isError,
        visualTransformation = visualTransformation
    )
}