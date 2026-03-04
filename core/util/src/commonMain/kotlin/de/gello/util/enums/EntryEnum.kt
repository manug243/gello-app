package de.gello.util.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Note
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.ui.graphics.vector.ImageVector

enum class EntryEnum(
    val id: Int,
    val icon: ImageVector
) {
    NOTE(1, Icons.AutoMirrored.Outlined.Note),
    IMAGE(2, Icons.Outlined.PictureAsPdf);

    fun findEntryTypeIdById(entryTypeId: Int): EntryEnum? {
        return EntryEnum.entries.find { entry -> entry.ordinal == entryTypeId }
    }
}