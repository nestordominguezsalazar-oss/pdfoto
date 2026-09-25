package com.pdfoto.domain.photo

import com.google.common.truth.Truth.assertThat
import com.pdfoto.domain.model.Photo
import org.junit.Test

class PhotoEditsTest {

    @Test
    fun `rotar avanza 90 grados`() {
        assertThat(rotatePhoto(Photo(id = "1", uri = "u")).rotationDegrees).isEqualTo(90)
    }

    @Test
    fun `rotar cuatro veces vuelve a 0`() {
        var photo = Photo(id = "1", uri = "u")

        repeat(4) { photo = rotatePhoto(photo) }

        assertThat(photo.rotationDegrees).isEqualTo(0)
    }

    @Test
    fun `eliminar quita la foto y renumera el orden`() {
        val photos = listOf(
            Photo(id = "a", uri = "u1", order = 0),
            Photo(id = "b", uri = "u2", order = 1),
            Photo(id = "c", uri = "u3", order = 2),
        )

        val result = removePhoto(photos, "b")

        assertThat(result.map { it.id }).containsExactly("a", "c").inOrder()
        assertThat(result.map { it.order }).containsExactly(0, 1).inOrder()
    }

    @Test
    fun `eliminar un id inexistente no cambia la lista`() {
        val photos = listOf(Photo(id = "a", uri = "u1", order = 0))

        assertThat(removePhoto(photos, "z")).isEqualTo(photos)
    }

    @Test
    fun `mover una foto reordena y renumera`() {
        val photos = listOf(
            Photo(id = "a", uri = "u1", order = 0),
            Photo(id = "b", uri = "u2", order = 1),
            Photo(id = "c", uri = "u3", order = 2),
        )

        val result = movePhoto(photos, fromIndex = 0, toIndex = 2)

        assertThat(result.map { it.id }).containsExactly("b", "c", "a").inOrder()
        assertThat(result.map { it.order }).containsExactly(0, 1, 2).inOrder()
    }

    @Test
    fun `mover con indices invalidos no cambia la lista`() {
        val photos = listOf(Photo(id = "a", uri = "u1", order = 0))

        assertThat(movePhoto(photos, fromIndex = 0, toIndex = 5)).isEqualTo(photos)
    }
}
