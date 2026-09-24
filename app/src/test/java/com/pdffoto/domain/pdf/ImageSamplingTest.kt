package com.pdffoto.domain.pdf

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ImageSamplingTest {

    @Test
    fun `una imagen 4000x3000 con maximo 2048 se reduce a la mitad`() {
        assertThat(calculateInSampleSize(width = 4000, height = 3000, maxDimension = 2048))
            .isEqualTo(2)
    }

    @Test
    fun `una imagen que ya cabe no se reduce`() {
        assertThat(calculateInSampleSize(width = 1000, height = 800, maxDimension = 2048))
            .isEqualTo(1)
    }

    @Test
    fun `una imagen muy grande se reduce mas`() {
        assertThat(calculateInSampleSize(width = 8000, height = 6000, maxDimension = 2048))
            .isEqualTo(4)
    }
}
