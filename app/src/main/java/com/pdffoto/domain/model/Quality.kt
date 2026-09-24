package com.pdffoto.domain.model

/**
 * Calidad del PDF, expresada como **DPI objetivo** de las imágenes incrustadas.
 *
 * Es el principal control del peso del archivo: menos píxeles = PDF más pequeño.
 *
 * @param dpi resolución objetivo (puntos por pulgada) al decodificar las fotos.
 */
enum class Quality(val dpi: Int) {
    LOW(96),
    MEDIUM(150),
    HIGH(200),
}
