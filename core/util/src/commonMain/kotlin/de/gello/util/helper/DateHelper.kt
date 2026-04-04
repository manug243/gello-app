package de.gello.util.helper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

object DateHelper {

    private val fullFormat = LocalDateTime.Format {
        day(Padding.ZERO)
        char('.')
        monthNumber(Padding.ZERO)
        char('.')
        year()
        char(' ')
        hour(Padding.ZERO)
        char(':')
        minute(Padding.ZERO)
    }

    fun dateFormat(date: String): String {
        val parts = date.split('-')
        return if (parts.size == 3) "${parts[2]}.${parts[1]}.${parts[0]}" else date
    }

    fun formateLocalDate(date: LocalDateTime): String {
        return date.format(LocalDateTime.Format {
            day(Padding.ZERO)
            char('.')
            monthNumber(Padding.ZERO)
            char('.')
            year()
        })
    }

    @OptIn(ExperimentalTime::class)
    fun formatDateString(date: String?): String {
        if (date.isNullOrBlank()) return "-"
        return try {
            val isoDate = if (date.endsWith("Z") || date.contains("+")) date else "${date}Z"
            kotlin.time.Instant.parse(isoDate)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(fullFormat)
        } catch (e: Exception) {
            "-"
        }
    }

    @OptIn(ExperimentalTime::class)
    fun formattedTimeStamp(): String {
        return kotlin.time.Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(fullFormat)
    }
}