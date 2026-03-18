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
import de.gello.app.feature.entryCreation.EntryCreationState
import de.gello.designsystem.component.ButtonNavigationToolbar
import de.gello.designsystem.component.ImagePreview
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.SecondaryButton
import de.gello.designsystem.component.StepProgressIndicator
import de.gello.designsystem.theme.Spacing

@Composable
internal fun EntryCreationThirdStepPage(
    state: EntryCreationState.ThirdStep,
    onCancelClick: () -> Unit,
    onNextStepClick: () -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            Text(
                text = "Do you want to use this optimized image for the analysis? Or would you like to do it manually?",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

            state.optimizedImage?.let {
                ImagePreview(it.data)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.Small)
            ) {
                PrimaryButton(
                    title = "Use this image",
                    onClick = {},
                    isEnabled = true
                )

                SecondaryButton(
                    title = "I'll do it myself",
                    onClick = {},
                    isEnabled = true
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            ButtonNavigationToolbar(
                onCancelClick = onCancelClick,
                onNextClick = onNextStepClick,
                isNextEnabled = state.draft.gelImage != null
            )

            StepProgressIndicator(
                currentStep = 3,
                totalSteps = 4
            )
        }
    }
}