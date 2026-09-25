package com.pdfoto.domain.pdf

/** Dimensiones de una página en puntos (1 pt = 1/72 de pulgada). */
data class PageDimensions(
    val widthPt: Int,
    val heightPt: Int,
)
