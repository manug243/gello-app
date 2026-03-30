package de.gello.app.feature.entryCreation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.gello.app.feature.entryCreation.EntryCreationState
import de.gello.designsystem.component.ButtonNavigationToolbar
import de.gello.designsystem.component.GelImageCarousel
import de.gello.designsystem.component.ImagePreview
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PagerDots
import de.gello.designsystem.component.StepProgressIndicator
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing

@Composable
internal fun EntryCreationThirdStepPage(
    state: EntryCreationState.ThirdStep,
    onLaneCountChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onNextStepClick: () -> Unit
) {
    val originalImage = state.draft.gelImage?.data
    val processedImage = state.optimizedImage?.data

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            Text(
                text = "This is the optimized image for the analysis. The analysis counted ${state.draft.content?.laneCount} lanes. You can check the original image by swiping. If this is correct click \"Next\"",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

            if (originalImage != null && processedImage != null) {
                GelImageCarousel(
                    originalImage = originalImage,
                    processedImage = processedImage
                )
            }

            Text(
                text = "If the counted lanes are incorrect, please enter the right amount of lanes.",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

            TextField(
                value = state.draft.laneCount ?: "",
                placeholder = "Lane count",
                onValueChanged = onLaneCountChange,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                isError = state.showError
            )
        }

        Spacer(Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            ButtonNavigationToolbar(
                onCancelClick = onCancelClick,
                onNextClick = onNextStepClick,
                isNextEnabled = state.draft.gelImage != null,
                primaryTitle = "Next"
            )

            StepProgressIndicator(
                currentStep = 3,
                totalSteps = 4
            )
        }
    }
}