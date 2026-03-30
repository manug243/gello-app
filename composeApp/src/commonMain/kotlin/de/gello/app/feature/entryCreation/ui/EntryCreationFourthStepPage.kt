package de.gello.app.feature.entryCreation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import de.gello.app.feature.entryCreation.EntryCreationState
import de.gello.designsystem.component.ButtonNavigationToolbar
import de.gello.designsystem.component.ImagePreview
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.StepProgressIndicator
import de.gello.designsystem.component.TextField
import de.gello.designsystem.theme.Spacing
import de.gello.domain.model.Lane
import kotlin.io.encoding.Base64

@Composable
internal fun EntryCreationFourthStepPage(
    state: EntryCreationState.FourthStep,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onProbeChange: (lane: String, value: String) -> Unit,
    onVolumeChange: (lane: String, value: String) -> Unit,
    onShowTableChange: (Boolean) -> Unit
) {

    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            state.draft.content?.tableData?.let { lanes ->
                LaneRow(lanes)
            }


            state.draft.content?.image?.let { base64 ->
                val bytes = remember(base64) {
                    Base64.decode(base64)
                }

                ImagePreview(bytes)
            }

            state.draft.content?.tableData?.let { lanes ->
                DataTableBlock(
                    showTable = state.showTable,
                    onShowTableChange = onShowTableChange,
                    lanes = lanes,
                    onProbeChange = onProbeChange,
                    onVolumeChange = onVolumeChange
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            ButtonNavigationToolbar(
                onCancelClick = onCancelClick,
                onNextClick = onSaveClick,
                isNextEnabled = true,
                primaryTitle = "Save"
            )

            StepProgressIndicator(
                currentStep = 4,
                totalSteps = 4
            )
        }
    }
}

@Composable
private fun DataTableBlock(
    showTable: Boolean,
    onShowTableChange: (Boolean) -> Unit,
    lanes: List<Lane>,
    onProbeChange: (lane: String, value: String) -> Unit,
    onVolumeChange: (lane: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you wish to add a data table?",
                style = MaterialTheme.typography.bodyMedium
            )

            Switch(
                checked = showTable,
                onCheckedChange = onShowTableChange
            )
        }

        if (showTable) {
            GelTable(
                lanes = lanes,
                modifier = Modifier.fillMaxWidth(),
                onProbeChange = onProbeChange,
                onVolumeChange = onVolumeChange
            )
        }
    }
}

@Composable
fun LaneRow(
    lanes: List<Lane>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Spacing.ExtraLarge,
                    end = Spacing.Large,
                    top = Spacing.Small,
                    bottom = Spacing.Small
                ),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            lanes.forEach { lane ->
                Text(
                    text = lane.lane,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        HorizontalDivider()
    }
}

@Composable
private fun GelTableRow(
    lane: Lane,
    onProbeChange: (lane: String, value: String) -> Unit,
    onVolumeChange: (lane: String, value: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.Small),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = lane.lane,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )

        TextField(
            value = lane.probe,
            placeholder = "Probe",
            onValueChanged = { onProbeChange(lane.lane, it) },
            modifier = Modifier.weight(4f)
        )

        TextField(
            value = lane.volume?.toString() ?: "",
            placeholder = "V",
            onValueChanged = { onVolumeChange(lane.lane, it) },
            keyboardType = KeyboardType.Number,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GelTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.Small),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
    ) {
        Text(
            text = "Lane",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Probe",
            modifier = Modifier.weight(4f),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "V [µL]",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun GelTable(
    lanes: List<Lane>,
    modifier: Modifier = Modifier,
    onProbeChange: (lane: String, value: String) -> Unit,
    onVolumeChange: (lane: String, value: String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider()

        GelTableHeader()

        HorizontalDivider()

        lanes.forEach { lane ->
            GelTableRow(
                lane = lane,
                onProbeChange = onProbeChange,
                onVolumeChange = onVolumeChange
            )
        }

        HorizontalDivider()

        Spacer(Modifier.height(Spacing.Medium))
    }
}