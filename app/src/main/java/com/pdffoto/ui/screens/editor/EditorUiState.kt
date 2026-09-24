package com.pdffoto.ui.screens.editor

import com.pdffoto.domain.model.Photo

/** Estado de la pantalla de edición. */
sealed interface EditorUiState {
    data object Empty : EditorUiState
    data class Content(val photos: List<Photo>) : EditorUiState
}
