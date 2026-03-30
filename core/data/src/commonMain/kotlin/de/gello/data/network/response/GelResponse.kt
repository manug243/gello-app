package de.gello.data.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GelResponse(
    @SerialName("image")
    val image: String,
    @SerialName("lane-count")
    val laneCount: Int,
    @SerialName("note")
    val note: String? = null,
    @SerialName("processed-image")
    val processedImage: String,
    @SerialName("table-data")
    val tableData: List<TableDataResponse>
)