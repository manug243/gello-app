package de.gello.app.feature.entryCreation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import de.gello.app.feature.entryCreation.EntryCreationState
import de.gello.designsystem.component.ButtonNavigationToolbar
import de.gello.designsystem.component.ClickableTextFieldWithDropdown
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.StepProgressIndicator
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing
import de.gello.util.enums.EntryEnum
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.placeholder_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EntryCreationFirstStepPage(
    state: EntryCreationState.FirstStep,
    onTitleChanged: (String) -> Unit,
    onNextStepClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSelectedItem: (EntryEnum) -> Unit
) {
    Page(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            Text(
                text = "Give your entry a meaningful title and choose the type of entry you want to create.",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

            TextField(
                value = state.draft.title,
                placeholder = stringResource(Res.string.placeholder_title),
                onValueChanged = onTitleChanged,
                isError = state.showError,
                imeAction = ImeAction.Done
            )

            ClickableTextFieldWithDropdown(
                items = state.entryOptions,
                placeholder = "Select entry type",
                value = { it.title },
                selectedItem = state.entryOptions.find { it.id == state.draft.typeId },
                onItemSelected = { item ->
                    onSelectedItem(item)
                }
            )
        }
        Spacer(Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            ButtonNavigationToolbar(
                onCancelClick = onCancelClick,
                onNextClick = onNextStepClick,
                isNextEnabled = state.allFirstFieldsFilled
            )

            StepProgressIndicator(
                currentStep = 1,
                totalSteps = 4
            )
        }
    }
}