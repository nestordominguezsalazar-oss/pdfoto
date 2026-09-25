package com.pdfoto.domain.pdf

/** Limpia un nombre de archivo para que sea válido y garantiza la extensión `.pdf`. */
object FileNameSanitizer {

    private val INVALID_CHARACTERS = Regex("[\\\\/:*?\"<>|]")
    private const val DEFAULT_NAME = "documento"
    private const val PDF_EXTENSION = ".pdf"

    fun sanitize(rawName: String): String {
        val cleaned = INVALID_CHARACTERS
            .replace(rawName.trim(), "_")
            .ifEmpty { DEFAULT_NAME }

        return if (cleaned.endsWith(PDF_EXTENSION, ignoreCase = true)) {
            cleaned
        } else {
            cleaned + PDF_EXTENSION
        }
    }
}
