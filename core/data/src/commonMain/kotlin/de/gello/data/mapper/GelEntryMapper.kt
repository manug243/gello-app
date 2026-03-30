package de.gello.data.mapper

import de.gello.data.network.response.GelResponse
import de.gello.data.network.response.TableDataResponse
import de.gello.domain.model.GelEntry
import de.gello.domain.model.Lane

internal fun GelResponse.toGelEntry(): GelEntry =
    GelEntry(
        image = image,
        processedImage = processedImage,
        laneCount = laneCount,
        tableData = tableData.map { it.toTableData() },
        note = note ?: ""
    )

internal fun TableDataResponse.toTableData(): Lane =
    Lane(
        lane = lane,
        probe = probe,
        volume = volume?.toIntOrNull()
    )