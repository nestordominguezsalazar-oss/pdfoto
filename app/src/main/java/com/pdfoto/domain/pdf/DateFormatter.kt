package com.pdfoto.domain.pdf

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/** Formatea una marca de tiempo como `dd/MM/yyyy HH:mm` (zona horaria configurable). */
fun formatTimestamp(
    timestampMillis: Long,
    timeZone: TimeZone = TimeZone.getDefault(),
): String {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).apply {
        this.timeZone = timeZone
    }
    return format.format(Date(timestampMillis))
}
