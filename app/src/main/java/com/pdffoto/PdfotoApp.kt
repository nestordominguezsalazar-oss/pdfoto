package com.pdffoto

import android.app.Application
import com.pdffoto.data.logging.AppLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Clase `Application` de PDFoto. Hilt la usa como contenedor raíz de dependencias.
 *
 * También registra un manejador de excepciones que guarda el fallo en el **log local** (sin
 * red), para poder adjuntarlo si el usuario decide enviar comentarios.
 */
@HiltAndroidApp
class PdfotoApp : Application() {

    @Inject
    lateinit var appLogger: AppLogger

    override fun onCreate() {
        super.onCreate()

        val previousHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            appLogger.logError("crash", throwable)
            previousHandler?.uncaughtException(thread, throwable)
        }
    }
}
