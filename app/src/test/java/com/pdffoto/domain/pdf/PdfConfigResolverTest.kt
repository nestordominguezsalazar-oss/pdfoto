package com.pdffoto.domain.pdf

import com.google.common.truth.Truth.assertThat
import com.pdffoto.domain.model.Orientation
import com.pdffoto.domain.model.PageSize
import com.pdffoto.domain.model.PdfConfig
import com.pdffoto.domain.model.Quality
import org.junit.Test

class PdfConfigResolverTest {

    @Test
    fun `A4 en vertical devuelve 595x842`() {
        val config = PdfConfig(pageSize = PageSize.A4, orientation = Orientation.PORTRAIT)

        val result = PdfConfigResolver.resolve(config, bitmapWidth = 2000, bitmapHeight = 1500)

        assertThat(result.widthPt).isEqualTo(595)
        assertThat(result.heightPt).isEqualTo(842)
    }

    @Test
    fun `A4 en horizontal intercambia las dimensiones`() {
        val config = PdfConfig(pageSize = PageSize.A4, orientation = Orientation.LANDSCAPE)

        val result = PdfConfigResolver.resolve(config, bitmapWidth = 2000, bitmapHeight = 1500)

        assertThat(result.widthPt).isEqualTo(842)
        assertThat(result.heightPt).isEqualTo(595)
    }

    @Test
    fun `AUTO usa las dimensiones del bitmap`() {
        val config = PdfConfig(pageSize = PageSize.AUTO, orientation = Orientation.AUTO)

        val result = PdfConfigResolver.resolve(config, bitmapWidth = 2000, bitmapHeight = 1500)

        assertThat(result.widthPt).isEqualTo(2000)
        assertThat(result.heightPt).isEqualTo(1500)
    }

    @Test
    fun `Carta en vertical devuelve 612x792`() {
        val config = PdfConfig(pageSize = PageSize.LETTER, orientation = Orientation.PORTRAIT)

        val result = PdfConfigResolver.resolve(config, bitmapWidth = 1000, bitmapHeight = 1000)

        assertThat(result.widthPt).isEqualTo(612)
        assertThat(result.heightPt).isEqualTo(792)
    }

    @Test
    fun `la resolución de decodificación depende del dpi de la calidad`() {
        // A4 alto (842 pt) a 150 dpi = 842 / 72 * 150 = 1754 px
        assertThat(PdfConfigResolver.targetDecodeDimension(PageSize.A4, Quality.MEDIUM)).isEqualTo(1754)
        assertThat(PdfConfigResolver.targetDecodeDimension(PageSize.A4, Quality.LOW)).isEqualTo(1123)
        assertThat(PdfConfigResolver.targetDecodeDimension(PageSize.A4, Quality.HIGH)).isEqualTo(2339)
    }

    @Test
    fun `la resolución de Carta y AUTO se calculan igual`() {
        // Carta alto (792 pt) a 150 dpi = 1650 px
        assertThat(PdfConfigResolver.targetDecodeDimension(PageSize.LETTER, Quality.MEDIUM)).isEqualTo(1650)
        // AUTO usa una referencia A4
        assertThat(PdfConfigResolver.targetDecodeDimension(PageSize.AUTO, Quality.MEDIUM)).isEqualTo(1754)
    }
}
