package com.pdffoto.ui.screens.editor

import androidx.lifecycle.ViewModel
import com.pdffoto.data.session.CreationSession
import com.pdffoto.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val session: CreationSession,
) : ViewModel() {

    /**
     * Fotos actuales del documento. Se expone el flujo de la sesión **directamente**
     * (sin `stateIn` con valor inicial) para que `value` sea siempre el estado real:
     * el editor decide con él si debe abrir el selector de fotos.
     */
    val photos: StateFlow<List<Photo>> = session.photos

    /** Añade al final las fotos elegidas en el selector (URIs como texto). */
    fun onPhotosPicked(uris: List<String>) = session.addPhotos(uris)

    /** Rota 90° la foto indicada. */
    fun onRotate(photoId: String) = session.rotate(photoId)

    /** Elimina la foto indicada. */
    fun onDelete(photoId: String) = session.delete(photoId)

    /** Reordena moviendo la foto de [fromIndex] a [toIndex]. */
    fun onMove(fromIndex: Int, toIndex: Int) = session.move(fromIndex, toIndex)
}
