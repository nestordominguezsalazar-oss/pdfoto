package com.pdffoto.domain.pdf

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DefaultFileNameTest {

    @Test
    fun `genera el nombre por defecto con el patron pdfoto_fecha`() {
        // 1700000000000 ms = 2023-11-14T22:13:20Z
        assertThat(defaultPdfFileName(1_700_000_000_000L)).isEqualTo("pdfoto_20231114_2213")
    }
}
