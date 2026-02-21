package de.gello.util.helper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

object DateHelper {

    fun dateFormat(date: String): String {
        val (year, month, day) = date.split('-')
        return "$day.$month.$year"
    }

    fun formateLocalDate(date: LocalDateTime): String {
        val format = LocalDateTime.Format {
            day()
            char('.')
            monthNumber()
            char('.')
            year()
        }

        return date.format(format)
    }

    fun formatDateString(date: String?): String {
        if (date.isNullOrBlank()) return "-"

        val cleaned = date.removeSuffix("Z")

        return try {
            val dateTimeParts = cleaned.split("T")
            val datePart = dateTimeParts.getOrNull(0) ?: return "-"
            val timePart = dateTimeParts.getOrNull(1)?.split(":")
            val hour = timePart?.getOrNull(0)?.toIntOrNull() ?: 0
            val minute = timePart?.getOrNull(1)?.toIntOrNull() ?: 0

            val correctedHour = (hour + 1) % 24

            val parts = datePart.split("-")
            if (parts.size != 3) return "-"
            val year = parts[0]
            val month = parts[1].padStart(2, '0')
            val day = parts[2].padStart(2, '0')

            "$day.$month.$year ${correctedHour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
        } catch (e: Exception) {
            "-"
        }
    }
}