package com.pdfoto.domain.model

/**
 * Configuración elegida por el usuario para generar el PDF.
 */
data class PdfConfig(
    val pageSize: PageSize = PageSize.AUTO,
    val orientation: Orientation = Orientation.AUTO,
    val margin: MarginSize = MarginSize.NONE,
    val quality: Quality = Quality.MEDIUM,
)
