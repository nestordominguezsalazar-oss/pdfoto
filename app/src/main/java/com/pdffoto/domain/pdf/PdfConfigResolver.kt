package com.pdffoto.domain.pdf

import com.pdffoto.domain.model.Orientation
import com.pdffoto.domain.model.PageSize
import com.pdffoto.domain.model.PdfConfig
import com.pdffoto.domain.model.Quality
import kotlin.math.roundToInt

/**
 * Resuelve el tamaño final de página (en puntos) a partir de la configuración del PDF.
 *
 * Tamaños base en vertical: A4 = 595x842, Carta = 612x792. En modo [PageSize.AUTO] se
 * usan las dimensiones del bitmap.
 */
object PdfConfigResolver {

    private val A4_PORTRAIT = PageDimensions(widthPt = 595, heightPt = 842)
    private val LETTER_PORTRAIT = PageDimensions(widthPt = 612, heightPt = 792)

    private const val A4_REFERENCE_PT = 842
    private const val LETTER_REFERENCE_PT = 792
    private const val POINTS_PER_INCH = 72.0

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

    /**
     * Lado mayor (en píxeles) al que decodificar la imagen, según el tamaño de página y la
     * calidad ([Quality.dpi]). Es el principal control del peso del PDF.
     */
    fun targetDecodeDimension(pageSize: PageSize, quality: Quality): Int {
        val referencePt = when (pageSize) {
            PageSize.A4 -> A4_REFERENCE_PT
            PageSize.LETTER -> LETTER_REFERENCE_PT
            PageSize.AUTO -> A4_REFERENCE_PT
        }
        return (referencePt / POINTS_PER_INCH * quality.dpi).roundToInt()
    }
}

private fun PageDimensions.withOrientation(orientation: Orientation): PageDimensions = when (orientation) {
    Orientation.PORTRAIT -> if (widthPt <= heightPt) this else PageDimensions(heightPt, widthPt)
    Orientation.LANDSCAPE -> if (widthPt >= heightPt) this else PageDimensions(heightPt, widthPt)
    Orientation.AUTO -> this
}
