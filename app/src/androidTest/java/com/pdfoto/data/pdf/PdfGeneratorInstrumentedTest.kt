package com.pdfoto.data.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.pdfoto.domain.model.Orientation
import com.pdfoto.domain.model.PageSize
import com.pdfoto.domain.model.PdfConfig
import com.pdfoto.domain.model.Photo
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test instrumentado de [PdfGenerator] de extremo a extremo: genera un PDF real desde
 * imágenes sintéticas y lo reabre con [PdfRenderer] para verificar páginas y tamaño.
 */
@RunWith(AndroidJUnit4::class)
class PdfGeneratorInstrumentedTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun generaUnPdfValidoConUnaPaginaPorFoto() = runBlocking {
        val fotos = listOf(
            crearFoto(context, "pdfoto_uno.png", Color.RED, order = 0),
            crearFoto(context, "pdfoto_dos.png", Color.BLUE, order = 1),
        )
        val salida = File(context.cacheDir, "pdfoto_dos_paginas.pdf")

        val resultado = PdfGenerator(context).generate(
            photos = fotos,
            config = PdfConfig(),
            outputFile = salida,
            onProgress = { _, _ -> },
        )

        assertThat(resultado.isSuccess).isTrue()
        assertThat(salida.length()).isGreaterThan(0)
        assertThat(contarPaginas(salida)).isEqualTo(2)
    }

    @Test
    fun respetaElTamanoA4EnVertical() = runBlocking {
        val salida = File(context.cacheDir, "pdfoto_a4.pdf")

        val resultado = PdfGenerator(context).generate(
            photos = listOf(crearFoto(context, "pdfoto_a4.png", Color.GREEN)),
            config = PdfConfig(pageSize = PageSize.A4, orientation = Orientation.PORTRAIT),
            outputFile = salida,
            onProgress = { _, _ -> },
        )

        assertThat(resultado.isSuccess).isTrue()
        ParcelFileDescriptor.open(salida, ParcelFileDescriptor.MODE_READ_ONLY).use { pfd ->
            PdfRenderer(pfd).use { renderer ->
                renderer.openPage(0).use { page ->
                    assertThat(page.width).isEqualTo(595)
                    assertThat(page.height).isEqualTo(842)
                }
            }
        }
    }

    @Test
    fun informaDelProgreso1Based() = runBlocking {
        val fotos = listOf(
            crearFoto(context, "pdfoto_p1.png", Color.RED, order = 0),
            crearFoto(context, "pdfoto_p2.png", Color.GREEN, order = 1),
        )
        val progreso = mutableListOf<Pair<Int, Int>>()

        PdfGenerator(context).generate(
            photos = fotos,
            config = PdfConfig(),
            outputFile = File(context.cacheDir, "pdfoto_progreso.pdf"),
            onProgress = { current, total -> progreso += current to total },
        )

        assertThat(progreso).containsExactly(1 to 2, 2 to 2).inOrder()
    }

    @Test
    fun omiteLasFotosQueNoDecodifican() = runBlocking {
        val valida = crearFoto(context, "pdfoto_valida.png", Color.RED, order = 0)
        val archivoInvalido = File(context.cacheDir, "pdfoto_invalida.png").apply {
            writeText("esto no es una imagen")
        }
        val invalida = Photo(
            id = "invalida",
            uri = Uri.fromFile(archivoInvalido).toString(),
            order = 1,
        )
        val salida = File(context.cacheDir, "pdfoto_con_invalida.pdf")

        val resultado = PdfGenerator(context).generate(
            photos = listOf(valida, invalida),
            config = PdfConfig(),
            outputFile = salida,
            onProgress = { _, _ -> },
        )

        // La foto que existe pero no decodifica se omite; solo la válida produce página.
        assertThat(resultado.isSuccess).isTrue()
        assertThat(contarPaginas(salida)).isEqualTo(1)
    }

    @Test
    fun omiteLasFotosConUriInaccesible() = runBlocking {
        val valida = crearFoto(context, "pdfoto_valida2.png", Color.RED, order = 0)
        val inaccesible = Photo(
            id = "inaccesible",
            uri = "file:///no/existe/pdfoto.png",
            order = 1,
        )
        val salida = File(context.cacheDir, "pdfoto_uri_inaccesible.pdf")

        val resultado = PdfGenerator(context).generate(
            photos = listOf(valida, inaccesible),
            config = PdfConfig(),
            outputFile = salida,
            onProgress = { _, _ -> },
        )

        // Una URI que no se puede abrir se omite; el PDF se genera con el resto.
        assertThat(resultado.isSuccess).isTrue()
        assertThat(contarPaginas(salida)).isEqualTo(1)
    }

    private fun contarPaginas(file: File): Int =
        ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY).use { pfd ->
            PdfRenderer(pfd).use { it.pageCount }
        }

    private fun crearFoto(context: Context, nombre: String, color: Int, order: Int = 0): Photo {
        val file = File(context.cacheDir, nombre)
        val bitmap = Bitmap.createBitmap(300, 400, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(color)
        FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
        return Photo(id = nombre, uri = Uri.fromFile(file).toString(), order = order)
    }
}
