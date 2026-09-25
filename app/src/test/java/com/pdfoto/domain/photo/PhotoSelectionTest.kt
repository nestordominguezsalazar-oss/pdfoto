package com.pdfoto.domain.photo

import com.google.common.truth.Truth.assertThat
import com.pdfoto.domain.model.Photo
import org.junit.Test

class PhotoSelectionTest {

    @Test
    fun `lista de uris vacia devuelve las existentes sin cambios`() {
        val existing = listOf(Photo(id = "id-1", uri = "u1", order = 0))

        assertThat(buildPhotos(existing, emptyList())).isEqualTo(existing)
    }

    @Test
    fun `asigna ids y orden consecutivo a partir de las existentes`() {
        val ids = listOf("id-2", "id-3").iterator()
        val existing = listOf(Photo(id = "id-1", uri = "u1", order = 0))

        val result = buildPhotos(existing, listOf("u2", "u3")) { ids.next() }

        assertThat(result).hasSize(3)
        assertThat(result.map { it.id }).containsExactly("id-1", "id-2", "id-3").inOrder()
        assertThat(result.map { it.order }).containsExactly(0, 1, 2).inOrder()
        assertThat(result.map { it.uri }).containsExactly("u1", "u2", "u3").inOrder()
    }

    @Test
    fun `las fotos nuevas empiezan sin rotacion`() {
        val result = buildPhotos(emptyList(), listOf("u1", "u2")) { "id" }

        assertThat(result.all { it.rotationDegrees == 0 }).isTrue()
    }
}
