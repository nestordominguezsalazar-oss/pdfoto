package com.pdfoto.domain.pdf

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FileNameSanitizerTest {

    @Test
    fun `reemplaza los caracteres invalidos por guion bajo`() {
        assertThat(FileNameSanitizer.sanitize("mi/archivo:raro?")).isEqualTo("mi_archivo_raro_.pdf")
    }

    @Test
    fun `añade la extension pdf si falta`() {
        assertThat(FileNameSanitizer.sanitize("documento")).isEqualTo("documento.pdf")
    }

    @Test
    fun `conserva la extension pdf existente`() {
        assertThat(FileNameSanitizer.sanitize("documento.pdf")).isEqualTo("documento.pdf")
    }

    @Test
    fun `un nombre vacio usa uno por defecto`() {
        assertThat(FileNameSanitizer.sanitize("   ")).isEqualTo("documento.pdf")
    }
}
