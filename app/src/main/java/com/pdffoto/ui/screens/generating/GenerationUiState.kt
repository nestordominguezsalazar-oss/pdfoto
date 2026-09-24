package com.pdffoto.ui.screens.generating

import com.pdffoto.domain.model.PdfJob

/** Estado del proceso de generación del PDF. */
sealed interface GenerationUiState {
    data object Idle : GenerationUiState
    data class InProgress(val current: Int, val total: Int) : GenerationUiState
    data class Success(val pdf: PdfJob) : GenerationUiState
    data class Error(val message: String?) : GenerationUiState
}
