package com.pdffoto.ui.screens.camera

import com.google.common.truth.Truth.assertThat
import com.pdffoto.data.logging.AppLogger
import com.pdffoto.data.session.CreationSession
import java.io.File
import org.junit.Test

class CameraViewModelTest {

    private fun viewModel(session: CreationSession) =
        CameraViewModel(session, AppLogger { File("/tmp/pdffoto-test.log") })

    @Test
    fun `la foto capturada se añade a la sesión`() {
        val session = CreationSession()
        val viewModel = viewModel(session)

        viewModel.onPhotoCaptured("file:///cache/captura.jpg")

        assertThat(session.photos.value.map { it.uri }).containsExactly("file:///cache/captura.jpg")
    }
}
