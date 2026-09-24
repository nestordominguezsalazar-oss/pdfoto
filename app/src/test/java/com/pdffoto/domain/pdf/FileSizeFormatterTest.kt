package com.pdffoto.domain.pdf

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FileSizeFormatterTest {

    @Test
    fun `tamano en bytes`() {
        assertThat(formatFileSize(512)).isEqualTo("512 B")
    }

    @Test
    fun `tamano en kilobytes`() {
        assertThat(formatFileSize(2048)).isEqualTo("2 KB")
    }

    @Test
    fun `tamano en megabytes con un decimal`() {
        assertThat(formatFileSize(1_572_864)).isEqualTo("1.5 MB")
    }
}
