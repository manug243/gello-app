package de.gello.app.feature.entryCreation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.attafitamim.krop.ui.ImageCropperDialog
import de.gello.app.feature.entryCreation.EntryCreationState
import de.gello.app.util.rememberFilePicker
import de.gello.app.util.rememberImageCropperLauncher
import de.gello.designsystem.component.ButtonNavigationToolbar
import de.gello.designsystem.component.ImagePreview
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.PrimaryButton
import de.gello.designsystem.component.SmallPrimaryButtonWithColor
import de.gello.designsystem.component.StepProgressIndicator
import de.gello.designsystem.theme.Spacing
import de.gello.domain.model.GelImage
import de.gello.util.enums.EntryEnum

@Composable
internal fun EntryCreationSecondStepPage(
    state: EntryCreationState.SecondStep,
    onCancelClick: () -> Unit,
    onCropCancelClick: () -> Unit,
    onNextStepClick: (GelImage) -> Unit,
    selectedImage: (GelImage) -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            when (state.draft.typeId) {
                EntryEnum.IMAGE.id -> GelImagePage(
                    state = state,
                    onSelectedImage = selectedImage,
                    onCropCancelClick = onCropCancelClick
                )

                EntryEnum.NOTE.id -> NotesPage()
            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            ButtonNavigationToolbar(
                onCancelClick = onCancelClick,
                onNextClick = { state.draft.gelImage?.let { onNextStepClick(it) } },
                isNextEnabled = state.imageSelected
            )

            StepProgressIndicator(
                currentStep = 2,
                totalSteps = 4
            )
        }
    }
}

@Composable
private fun GelImagePage(
    state: EntryCreationState.SecondStep,
    onSelectedImage: (GelImage) -> Unit,
    onCropCancelClick: () -> Unit,
) {
    val singleLauncher = rememberFilePicker { onSelectedImage(it) }

    val imageCropperLauncher = rememberImageCropperLauncher(
        onCropCancelled = onCropCancelClick,
        onCropFailed = { println("Crop failed") },
        onCropSuccess = onSelectedImage
    )

    Text(
        text = "Select an image to analyze and extract relevant data for your entry.",
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium
    )

    PrimaryButton(
        onClick = { singleLauncher.launch() },
        title = if (state.draft.gelImage == null) "Select image" else "Replace image",
        isEnabled = true
    )

    state.draft.gelImage?.let {
        Column {
            ImagePreview(state.draft.gelImage.data)

            Spacer(Modifier.height(Spacing.Small))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Adjust the crop area if needed.",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.weight(1f))

                SmallPrimaryButtonWithColor(
                    onClick = { imageCropperLauncher.launch(state.draft.gelImage) },
                    title = "Crop"
                )
            }
        }
    }

    imageCropperLauncher.cropState?.let { cropState ->
        ImageCropperDialog(
            state = cropState
        )
    }
}

@Composable
private fun NotesPage() {
    Text(text = "Coming soon. We're working on it.")
}