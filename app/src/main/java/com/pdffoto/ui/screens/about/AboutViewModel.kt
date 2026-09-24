package com.pdffoto.ui.screens.about

import android.os.Build
import androidx.lifecycle.ViewModel
import com.pdffoto.BuildConfig
import com.pdffoto.data.logging.AppLogger
import com.pdffoto.domain.support.buildFeedbackDiagnostics
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val logger: AppLogger,
) : ViewModel() {

    val versionName: String = BuildConfig.VERSION_NAME

    /** Diagnósticos técnicos (sin datos personales) para el correo de comentarios. */
    fun feedbackDiagnostics(): String = buildFeedbackDiagnostics(
        versionName = BuildConfig.VERSION_NAME,
        versionCode = BuildConfig.VERSION_CODE,
        androidRelease = Build.VERSION.RELEASE,
        sdkInt = Build.VERSION.SDK_INT,
        manufacturer = Build.MANUFACTURER,
        model = Build.MODEL,
    )

    /** Log local (puede adjuntarse al reporte). */
    fun feedbackLogFile(): File = logger.logFile
}
