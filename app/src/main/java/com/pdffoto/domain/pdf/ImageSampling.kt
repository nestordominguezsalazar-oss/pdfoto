package com.pdffoto.domain.pdf

import kotlin.math.max

/**
 * Calcula el mayor `inSampleSize` (potencia de 2) tal que la dimensión mayor de la
 * imagen decodificada no supere [maxDimension]. Evita cargar bitmaps a resolución
 * completa en memoria.
 */
fun calculateInSampleSize(width: Int, height: Int, maxDimension: Int): Int {
    if (width <= 0 || height <= 0 || maxDimension <= 0) return 1

    val largest = max(width, height)
    var sampleSize = 1
    while (largest / sampleSize > maxDimension) {
        sampleSize *= 2
    }
    return sampleSize
}
