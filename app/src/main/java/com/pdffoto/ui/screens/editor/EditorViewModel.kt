package com.pdffoto.ui.screens.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdffoto.data.session.CreationSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val session: CreationSession,
) : ViewModel() {

    val uiState: StateFlow<EditorUiState> = session.photos
        .map { list -> if (list.isEmpty()) EditorUiState.Empty else EditorUiState.Content(list) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = EditorUiState.Empty,
        )

    /** Añade al final las fotos elegidas en el selector (URIs como texto). */
    fun onPhotosPicked(uris: List<String>) = session.addPhotos(uris)

    /** Rota 90° la foto indicada. */
    fun onRotate(photoId: String) = session.rotate(photoId)

    /** Elimina la foto indicada. */
    fun onDelete(photoId: String) = session.delete(photoId)

    /** Reordena moviendo la foto de [fromIndex] a [toIndex]. */
    fun onMove(fromIndex: Int, toIndex: Int) = session.move(fromIndex, toIndex)

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
