package de.gello.app.feature.journalCreation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.app.feature.journalCreation.JournalCreationState.Default
import de.gello.app.feature.journalCreation.JournalCreationState.Intent
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing

@Composable
internal fun JournalCreationPage(
    state: Default,
    onTitleChanged: (String) -> Unit,
    onCreateClick: () -> Unit
) {
    Page(
        modifier = Modifier
            .padding(horizontal = Spacing.Medium)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            InputFields(
                state = state,
                onTitleChanged = onTitleChanged
            )

            PrimaryButton(
                title = "Create journal",
                isEnabled = true,
                onClick = onCreateClick
            )
        }
    }
}

@Composable
private fun InputFields(
    state: Default,
    onTitleChanged: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        TextField(
            value = state.journal.title,
            placeholder = "Title",
            onValueChanged = onTitleChanged,
            isError = state.showError
        )
    }
}