package com.pdffoto.domain.model

/**
 * Una foto seleccionada por el usuario para incluir en el PDF.
 *
 * @param uri URI del contenido original como texto. Se usa `String` (y no
 *   `android.net.Uri`) para mantener la capa de dominio libre de dependencias de
 *   Android y poder testarla en la JVM.
 * @param rotationDegrees rotación aplicada: 0, 90, 180 o 270.
 * @param order posición dentro del documento (menor = antes).
 */
data class Photo(
    val id: String,
    val uri: String,
    val rotationDegrees: Int = 0,
    val order: Int = 0,
)
