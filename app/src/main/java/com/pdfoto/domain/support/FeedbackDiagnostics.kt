package com.pdfoto.domain.support

/**
 * Datos técnicos (sin información personal) para adjuntar a un reporte de comentarios:
 * versión de la app, versión de Android y dispositivo.
 */
fun buildFeedbackDiagnostics(
    versionName: String,
    versionCode: Int,
    androidRelease: String,
    sdkInt: Int,
    manufacturer: String,
    model: String,
): String = buildString {
    appendLine("App version: $versionName ($versionCode)")
    appendLine("Android: $androidRelease (API $sdkInt)")
    appendLine("Device: $manufacturer $model")
}
