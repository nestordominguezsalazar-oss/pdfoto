package com.pdffoto.data.pdf

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

/**
 * Recomprime el bitmap como JPEG con la calidad indicada (0-100), de forma que la
 * opción "Calidad" de la configuración tenga efecto real en el PDF resultante.
 *
 * Si la decodificación del JPEG fallara, devuelve el bitmap original.
 */
fun recompressAsJpeg(bitmap: Bitmap, quality: Int): Bitmap {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    val bytes = stream.toByteArray()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size) ?: bitmap
}
