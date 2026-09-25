package com.pdfoto.data.logging

import java.io.File

/**
 * Log local en archivo con límite de líneas (bucle). **No envía nada por red**: solo se
 * usa si el usuario decide adjuntarlo al enviar comentarios.
 */
class FileLogger(
    private val file: File,
    private val maxLines: Int = DEFAULT_MAX_LINES,
) {

    @Synchronized
    fun log(line: String) {
        file.parentFile?.mkdirs()
        file.appendText(line + "\n")
        trim()
    }

    @Synchronized
    fun read(): String = if (file.exists()) file.readText() else ""

    @Synchronized
    fun clear() {
        if (file.exists()) file.delete()
    }

    private fun trim() {
        val lines = file.readLines()
        if (lines.size > maxLines) {
            file.writeText(lines.takeLast(maxLines).joinToString(separator = "\n", postfix = "\n"))
        }
    }

    companion object {
        const val DEFAULT_MAX_LINES = 500
    }
}
