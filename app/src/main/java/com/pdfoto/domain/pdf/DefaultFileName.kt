package com.pdfoto.domain.pdf

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Nombre de archivo por defecto: `pdfoto_yyyyMMdd_HHmm`, en UTC para que sea estable.
 */
fun defaultPdfFileName(timestampMillis: Long): String {
    val format = SimpleDateFormat("yyyyMMdd_HHmm", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return "pdfoto_${format.format(Date(timestampMillis))}"
}
