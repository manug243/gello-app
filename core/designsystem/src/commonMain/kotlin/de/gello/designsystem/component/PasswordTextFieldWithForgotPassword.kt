package de.gello.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun PasswordTextFieldWithForgotPassword(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    contentType: ContentType = ContentType.Password,
    imeAction: ImeAction = ImeAction.Next,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    supportingText: String? = null,
    isError: Boolean = false,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .semantics { this.contentType = contentType }
                    .then(modifier),
            value = value,
            placeholder = {
                Text(placeholder)
            },
            label = {
                Text(placeholder)
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = null,
                    )
                }
            },
            supportingText = {
                supportingText?.let { Text(text = it) }
            },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = imeAction,
                ),
            isError = isError,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = onPasswordChange,
            singleLine = true,
        )

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onForgotPasswordClick,
        ) {
            Text(text = "Forgot password?", textDecoration = TextDecoration.Underline)
        }
    }
}