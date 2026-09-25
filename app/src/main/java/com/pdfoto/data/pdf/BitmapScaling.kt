package com.pdfoto.data.pdf

import android.graphics.Bitmap
import androidx.core.graphics.scale
import kotlin.math.roundToInt

/**
 * Reduce el bitmap para que su lado mayor no supere [maxDimension], manteniendo la
 * proporción. Si ya cabe (o el tamaño es inválido) devuelve el mismo bitmap.
 *
 * Red de seguridad: el muestreo por potencias de 2 puede dejar la imagen por encima del
 * objetivo cuando no se conocen las dimensiones reales.
 */
fun Bitmap.scaledDownTo(maxDimension: Int): Bitmap {
    val largest = maxOf(width, height)
    if (maxDimension <= 0 || largest <= maxDimension) return this

    val factor = maxDimension.toFloat() / largest
    val targetWidth = (width * factor).roundToInt().coerceAtLeast(1)
    val targetHeight = (height * factor).roundToInt().coerceAtLeast(1)

    return scale(targetWidth, targetHeight, filter = true).also {
        if (it !== this) recycle()
    }
}
