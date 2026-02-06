package de.gello.designsystem.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import de.gello.designsystem.theme.Spacing

@Composable
fun Page(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(Spacing.Medium),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    autoClearFocus: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    BasePage(
        modifier =
            Modifier
                .padding(padding)
                .then(modifier),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        autoClearFocus = autoClearFocus,
        content = content,
    )
}

@Composable
fun PageWithPaddingSlot(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(Spacing.Medium),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    autoClearFocus: Boolean = true,
    content: @Composable ColumnScope.(padding: PaddingValues) -> Unit,
) {
    BasePage(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        autoClearFocus = autoClearFocus,
    ) {
        content(padding)
    }
}

@Composable
private fun BasePage(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    autoClearFocus: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val focusClearingModifier =
        if (autoClearFocus) {
            Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
        } else {
            Modifier
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .then(focusClearingModifier)
                .then(modifier),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content,
    )
}