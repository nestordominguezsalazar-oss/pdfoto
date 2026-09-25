package com.pdfoto.ui.screens.camera

import com.google.common.truth.Truth.assertThat
import com.pdfoto.data.logging.AppLogger
import com.pdfoto.data.session.CreationSession
import java.io.File
import org.junit.Test

class CameraViewModelTest {

    private fun viewModel(session: CreationSession) =
        CameraViewModel(session, AppLogger { File("/tmp/pdfoto-test.log") })

    @Test
    fun `la foto capturada se añade a la sesión`() {
        val session = CreationSession()
        val viewModel = viewModel(session)

        viewModel.onPhotoCaptured("file:///cache/captura.jpg")

        assertThat(session.photos.value.map { it.uri }).containsExactly("file:///cache/captura.jpg")
    }
}
