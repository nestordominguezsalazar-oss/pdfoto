package com.pdffoto.domain.photo

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExifOrientationTest {

    @Test
    fun `orientacion normal no transforma`() {
        assertThat(exifOrientationToTransform(1)).isEqualTo(ImageOrientation(0, false))
    }

    @Test
    fun `orientacion desconocida no transforma`() {
        assertThat(exifOrientationToTransform(0)).isEqualTo(ImageOrientation(0, false))
    }

    @Test
    fun `rotar 90 en sentido horario`() {
        assertThat(exifOrientationToTransform(6)).isEqualTo(ImageOrientation(90, false))
    }

    @Test
    fun `rotar 180`() {
        assertThat(exifOrientationToTransform(3)).isEqualTo(ImageOrientation(180, false))
    }

    @Test
    fun `rotar 270`() {
        assertThat(exifOrientationToTransform(8)).isEqualTo(ImageOrientation(270, false))
    }

    @Test
    fun `espejo horizontal`() {
        assertThat(exifOrientationToTransform(2)).isEqualTo(ImageOrientation(0, true))
    }
}
