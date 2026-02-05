package de.gello.util.helper

fun parseHexColor(hex: String): Long? {
    val cleaned = hex
        .trim()
        .removePrefix("#")

    if (cleaned.length != 6 && cleaned.length != 8) return null

    val value = cleaned.toLongOrNull(16) ?: return null

    return if (cleaned.length == 6) {
        0xFF000000 or value
    } else {
        value
    }
}