package com.pdfoto.domain.pdf

import java.util.Locale

private const val KB = 1024L
private const val MB = 1024L * 1024L

/** Formatea un tamaño en bytes de forma legible: `512 B`, `2 KB`, `1.5 MB`. */
fun formatFileSize(bytes: Long): String = when {
    bytes >= MB -> String.format(Locale.US, "%.1f MB", bytes.toDouble() / MB)
    bytes >= KB -> String.format(Locale.US, "%.0f KB", bytes.toDouble() / KB)
    else -> "$bytes B"
}
