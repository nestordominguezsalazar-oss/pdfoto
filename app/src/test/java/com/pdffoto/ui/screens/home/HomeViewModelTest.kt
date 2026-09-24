package com.pdffoto.ui.screens.home

import com.google.common.truth.Truth.assertThat
import com.pdffoto.data.logging.AppLogger
import com.pdffoto.data.session.CreationSession
import java.io.File
import org.junit.Test

class HomeViewModelTest {

    private fun viewModel(session: CreationSession) =
        HomeViewModel(session, AppLogger { File("/tmp/pdffoto-test.log") })

    @Test
    fun `startNewCreation limpia fotos y configuración`() {
        val session = CreationSession()
        session.addPhotos(listOf("content://foto/vieja"))
        session.setFileName("documento_viejo")

        viewModel(session).startNewCreation()

        assertThat(session.photos.value).isEmpty()
        assertThat(session.config.value.fileName).matches("pdfoto_\\d{8}_\\d{4}")
    }
}
