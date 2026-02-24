package de.gello.util.helper

import androidx.compose.ui.graphics.Color

fun parseHexColor(hex: String): Color {
    val cleaned = hex.trim().removePrefix("#")

    val value = cleaned.toLongOrNull(16) ?: return Color.Blue

    return when (cleaned.length) {
        6 -> Color(0xFF000000 or value)
        8 -> Color(value)
        else -> Color.Blue
    }
}