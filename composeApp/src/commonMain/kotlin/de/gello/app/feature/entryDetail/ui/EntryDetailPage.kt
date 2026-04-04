package de.gello.app.feature.entryDetail.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.gello.app.feature.entryCreation.ui.LaneRow
import de.gello.app.feature.entryDetail.EntryDetailState
import de.gello.designsystem.component.ImagePreview
import de.gello.designsystem.component.PageWithPaddingSlot
import de.gello.designsystem.theme.Spacing
import de.gello.domain.model.Lane
import kotlin.io.encoding.Base64

@Composable
internal fun EntryDetailPage(
    state: EntryDetailState.Default
) {
    PageWithPaddingSlot(
        modifier = Modifier
            .padding(horizontal = Spacing.Medium)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            state.gelEntry?.tableData?.let { lanes ->
                LaneRow(lanes)
            }

            state.gelEntry?.image?.let { base64 ->
                val bytes = remember(base64) {
                    Base64.decode(base64)
                }

                ImagePreview(bytes)
            }

            state.gelEntry?.note?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            state.gelEntry?.tableData
                ?.takeIf { lanes ->
                    lanes.any{ it.probe.isNotBlank() || it.volume != null }
                }
                ?.let { lanes ->
                    GelTable(lanes = lanes)
                }
        }
    }
}

@Composable
private fun GelTableRow(
    lane: Lane,
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

        Text(
            text = lane.probe,
            modifier = Modifier.weight(4f),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = lane.volume.toString(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
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
            text = "Sample",
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
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider()

        GelTableHeader()

        HorizontalDivider()

        lanes.forEach { lane ->
            GelTableRow(
                lane = lane
            )
        }

        HorizontalDivider()

        Spacer(Modifier.height(Spacing.Medium))
    }
}