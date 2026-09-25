package com.pdfoto.ui.screens.home

import com.google.common.truth.Truth.assertThat
import com.pdfoto.data.session.CreationSession
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun `startNewCreation limpia fotos y configuración`() {
        val session = CreationSession()
        session.addPhotos(listOf("content://foto/vieja"))
        session.setFileName("documento_viejo")

        HomeViewModel(session).startNewCreation()

        assertThat(session.photos.value).isEmpty()
        assertThat(session.config.value.fileName).matches("pdfoto_\\d{8}_\\d{4}")
    }
}
