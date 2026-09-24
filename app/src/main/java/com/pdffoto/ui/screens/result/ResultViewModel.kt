package com.pdffoto.ui.screens.result

import androidx.lifecycle.ViewModel
import com.pdffoto.data.session.CreationSession
import com.pdffoto.domain.model.PdfJob
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ResultViewModel @Inject constructor(
    session: CreationSession,
) : ViewModel() {

    /** Último PDF generado (null si no hay ninguno). */
    val result: StateFlow<PdfJob?> = session.lastResult
}
