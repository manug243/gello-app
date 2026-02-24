package de.gello.app.feature.journalCreation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import de.gello.app.feature.journalCreation.JournalCreationState.Default
import de.gello.designsystem.component.ColorPickerDialog
import de.gello.designsystem.component.JournalCard
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.SmallPrimaryButtonWithColor
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing
import de.gello.util.helper.DateHelper
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.button_create_journal
import gello.composeapp.generated.resources.button_select
import gello.composeapp.generated.resources.create_journal_select_color
import gello.composeapp.generated.resources.placeholder_description
import gello.composeapp.generated.resources.placeholder_title
import gello.composeapp.generated.resources.preview
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun JournalCreationPage(
    state: Default,
    onTitleChanged: (String) -> Unit,
    onDescChanged: (String) -> Unit,
    onColorChanged: (String) -> Unit,
    onCreateClick: () -> Unit
) {
    var showColorDialog by remember { mutableStateOf(false) }

    if (showColorDialog) {
        ColorPickerDialog(
            initialColor = state.journal.color,
            onColorConfirmed = { color ->
                onColorChanged(color)
                showColorDialog = false
            },
            onDismiss = { showColorDialog = false }
        )
    }

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            InputFields(
                state = state,
                onTitleChanged = onTitleChanged,
                onDescChanged = onDescChanged
            )

            SelectColorIndicator(
                onClick = { showColorDialog = true }
            )

            AnimatedVisibility(state.allFieldsFilled) {
                Column {
                    Text(
                        text = stringResource(Res.string.preview)
                    )

                    Spacer(Modifier.height(Spacing.Small))

                    JournalCard(
                        colorIndicator = state.journal.color,
                        title = state.journal.title,
                        owner = state.journal.owner,
                        updatedAt = DateHelper.formattedTimeStamp(),
                        entryCount = 0,
                        onClick = {}
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                title = stringResource(Res.string.button_create_journal),
                isEnabled = state.allFieldsFilled,
                onClick = onCreateClick
            )
        }
    }
}

@Composable
private fun InputFields(
    state: Default,
    onTitleChanged: (String) -> Unit,
    onDescChanged: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        TextField(
            value = state.journal.title,
            placeholder = stringResource(Res.string.placeholder_title),
            onValueChanged = onTitleChanged,
            isError = state.showError,
            imeAction = ImeAction.Next
        )

        TextField(
            value = state.journal.description.orEmpty(),
            placeholder = stringResource(Res.string.placeholder_description),
            onValueChanged = onDescChanged,
            isError = state.showError,
            imeAction = ImeAction.Done
        )
    }
}

@Composable
private fun SelectColorIndicator(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(Res.string.create_journal_select_color))

        Spacer(Modifier.weight(1f))

        SmallPrimaryButtonWithColor(
            onClick = onClick,
            title = stringResource(Res.string.button_select)
        )
    }
}