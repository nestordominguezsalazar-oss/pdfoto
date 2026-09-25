package com.pdfoto.data.logging

import java.io.File

/** Devuelve el archivo de log local de la app. */
fun interface LogFileProvider {
    fun logFile(): File
}
