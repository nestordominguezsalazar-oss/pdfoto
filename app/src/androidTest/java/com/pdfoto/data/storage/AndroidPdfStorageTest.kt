package com.pdfoto.data.storage

import android.content.Context
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import java.io.File
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test instrumentado de [AndroidPdfStorage]: ejercita la ruta real (MediaStore en API 29+,
 * FileProvider por debajo) y comprueba que el PDF se puede volver a leer.
 */
@RunWith(AndroidJUnit4::class)
class AndroidPdfStorageTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun guardaElPdfYSePuedeVolverALeer() = runBlocking {
        val origen = File(context.cacheDir, "pdfoto_origen.pdf").apply {
            writeText("%PDF-1.4 contenido de prueba")
        }
        val nombre = "pdfoto_test_${System.currentTimeMillis()}.pdf"

        val resultado = AndroidPdfStorage(context).saveToDownloads(origen, nombre)

        assertThat(resultado.isSuccess).isTrue()
        val guardado = resultado.getOrThrow()
        assertThat(guardado.displayName).isEqualTo(nombre)
        assertThat(guardado.sizeBytes).isEqualTo(origen.length())

        val uri = guardado.uri.toUri()
        val contenido = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: byteArrayOf()
        assertThat(String(contenido)).contains("contenido de prueba")

        // Limpieza: en API 29+ la fila de MediaStore queda en Downloads del emulador/dispositivo.
        runCatching { context.contentResolver.delete(uri, null, null) }
        Unit
    }
}
