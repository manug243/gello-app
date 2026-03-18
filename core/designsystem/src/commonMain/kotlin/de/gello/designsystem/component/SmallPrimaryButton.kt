package de.gello.designsystem.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SmallPrimaryButtonWithColor(
    onClick: () -> Unit,
    title: String
) {
    Button(
        modifier = Modifier
            .heightIn(ButtonDefaults.ExtraSmallContainerHeight),
        onClick = onClick,
        content = {
            Text(
                text = title,
                style = ButtonDefaults.textStyleFor(ButtonDefaults.ExtraSmallContainerHeight),
                maxLines = 1
            )
        },
        contentPadding = ButtonDefaults.ExtraSmallContentPadding
    )
}