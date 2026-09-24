package com.pdffoto.domain.pdf

import com.pdffoto.domain.model.Orientation
import com.pdffoto.domain.model.PageSize
import com.pdffoto.domain.model.PdfConfig

/**
 * Resuelve el tamaño final de página (en puntos) a partir de la configuración del PDF.
 *
 * Tamaños base en vertical: A4 = 595x842, Carta = 612x792. En modo [PageSize.AUTO] se
 * usan las dimensiones del bitmap.
 */
object PdfConfigResolver {

    private val A4_PORTRAIT = PageDimensions(widthPt = 595, heightPt = 842)
    private val LETTER_PORTRAIT = PageDimensions(widthPt = 612, heightPt = 792)

    private const val A4_MAX_DIMENSION = 2480   // ~300 dpi
    private const val LETTER_MAX_DIMENSION = 2550
    private const val AUTO_MAX_DIMENSION = 4096

    fun resolve(config: PdfConfig, bitmapWidth: Int, bitmapHeight: Int): PageDimensions {
        val base = when (config.pageSize) {
            PageSize.A4 -> A4_PORTRAIT
            PageSize.LETTER -> LETTER_PORTRAIT
            PageSize.AUTO -> PageDimensions(widthPt = bitmapWidth, heightPt = bitmapHeight)
        }
        val orientation = when (config.orientation) {
            Orientation.PORTRAIT -> Orientation.PORTRAIT
            Orientation.LANDSCAPE -> Orientation.LANDSCAPE
            Orientation.AUTO ->
                if (bitmapWidth >= bitmapHeight) Orientation.LANDSCAPE else Orientation.PORTRAIT
        }
        return base.withOrientation(orientation)
    }

    /** Lado mayor al que conviene decodificar una imagen para este tamaño de página. */
    fun maxDecodeDimension(pageSize: PageSize): Int = when (pageSize) {
        PageSize.A4 -> A4_MAX_DIMENSION
        PageSize.LETTER -> LETTER_MAX_DIMENSION
        PageSize.AUTO -> AUTO_MAX_DIMENSION
    }
}

private fun PageDimensions.withOrientation(orientation: Orientation): PageDimensions = when (orientation) {
    Orientation.PORTRAIT -> if (widthPt <= heightPt) this else PageDimensions(heightPt, widthPt)
    Orientation.LANDSCAPE -> if (widthPt >= heightPt) this else PageDimensions(heightPt, widthPt)
    Orientation.AUTO -> this
}
