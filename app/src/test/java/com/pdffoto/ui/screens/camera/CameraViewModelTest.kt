package com.pdffoto.ui.screens.camera

import com.google.common.truth.Truth.assertThat
import com.pdffoto.data.session.CreationSession
import org.junit.Test

class CameraViewModelTest {

    @Test
    fun `la foto capturada se añade a la sesión`() {
        val session = CreationSession()
        val viewModel = CameraViewModel(session)

        viewModel.onPhotoCaptured("file:///cache/captura.jpg")

        assertThat(session.photos.value.map { it.uri }).containsExactly("file:///cache/captura.jpg")
    }
}
