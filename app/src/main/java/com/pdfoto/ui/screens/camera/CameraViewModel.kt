package com.pdfoto.ui.screens.camera

import androidx.lifecycle.ViewModel
import com.pdfoto.data.logging.AppLogger
import com.pdfoto.data.session.CreationSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val session: CreationSession,
    private val logger: AppLogger,
) : ViewModel() {

    /** Añade al final la foto recién capturada. */
    fun onPhotoCaptured(uri: String) = session.addPhotos(listOf(uri))

    /** Registra un fallo de captura en el log local. */
    fun onCaptureError(throwable: Throwable) = logger.logError("camera", throwable)
}
