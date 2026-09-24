package com.pdffoto.ui.screens.history

import com.pdffoto.domain.model.PdfJob

/** Estado de la pantalla de historial. */
sealed interface HistoryUiState {
    data object Empty : HistoryUiState
    data class Content(val items: List<PdfJob>) : HistoryUiState
}
