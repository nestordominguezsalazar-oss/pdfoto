package com.pdffoto.ui.screens.editor

import com.google.common.truth.Truth.assertThat
import com.pdffoto.data.session.CreationSession
import org.junit.Test

class EditorViewModelTest {

    @Test
    fun `las fotos reflejan la sesion inmediatamente`() {
        val session = CreationSession()
        val viewModel = EditorViewModel(session)

        session.addPhotos(listOf("content://foto/1", "content://foto/2"))

        // El valor debe ser correcto al instante (el editor decide con él si abrir el picker).
        assertThat(viewModel.photos.value.map { it.uri })
            .containsExactly("content://foto/1", "content://foto/2")
            .inOrder()
    }

    @Test
    fun `rotar y eliminar delegan en la sesión`() {
        val session = CreationSession()
        val viewModel = EditorViewModel(session)
        session.addPhotos(listOf("content://foto/1"))
        val id = session.photos.value.first().id

        viewModel.onRotate(id)
        assertThat(session.photos.value.first().rotationDegrees).isEqualTo(90)

        viewModel.onDelete(id)
        assertThat(session.photos.value).isEmpty()
    }

    @Test
    fun `mover delega en la sesión`() {
        val session = CreationSession()
        val viewModel = EditorViewModel(session)
        session.addPhotos(listOf("a", "b", "c"))

        viewModel.onMove(0, 2)

        assertThat(session.photos.value.map { it.uri }).containsExactly("b", "c", "a").inOrder()
    }
}
