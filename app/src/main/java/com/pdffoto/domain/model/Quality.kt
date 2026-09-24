package com.pdffoto.domain.model

/**
 * Calidad de compresión JPEG.
 *
 * @param jpeg valor 0-100 usado al comprimir/decodificar las imágenes.
 */
enum class Quality(val jpeg: Int) {
    LOW(60),
    MEDIUM(80),
    HIGH(95),
}
