package com.pdfoto.data.logging

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Log local de la app (sin red). Registra errores y eventos para poder adjuntarlos cuando
 * el usuario decide enviar comentarios.
 */
@Singleton
class AppLogger @Inject constructor(
    private val logFileProvider: LogFileProvider,
) {

    private val fileLogger = FileLogger(logFileProvider.logFile())

    val logFile: File get() = logFileProvider.logFile()

    fun log(tag: String, message: String) {
        fileLogger.log("${timestamp()} [$tag] $message")
    }

    fun logError(tag: String, throwable: Throwable) {
        log(tag, "ERROR ${throwable.javaClass.simpleName}: ${throwable.message}")
    }

    private fun timestamp(): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
}
