package de.gello.util.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Note
import androidx.compose.material.icons.outlined.DeviceUnknown
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.ui.graphics.vector.ImageVector

enum class EntryEnum(
    val id: Int,
    val icon: ImageVector,
    val title: String
) {
    NOTE(1, Icons.AutoMirrored.Outlined.Note, "Notes"),
    IMAGE(2, Icons.Outlined.PictureAsPdf, "Gel analysis"),
    UNKNOWN(-1, Icons.Outlined.DeviceUnknown, "Unknown");

    companion object {
        fun findTitleById(typeId: Int?): String? {
            return EntryEnum.entries
                .find { it.id == typeId }
                ?.title
        }
    }
}