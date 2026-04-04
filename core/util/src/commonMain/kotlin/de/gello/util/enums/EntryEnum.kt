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
    IMAGE(1, Icons.Outlined.PictureAsPdf, "Gel analysis"),
    NOTE(2, Icons.AutoMirrored.Outlined.Note, "Notes"),
    UNKNOWN(-1, Icons.Outlined.DeviceUnknown, "Unknown");

    companion object {
        fun fromType(type: String?): EntryEnum {
            return entries.firstOrNull { it.name == type } ?: NOTE
        }
    }
}