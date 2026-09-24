package com.pdffoto.domain.model

/**
 * Un PDF ya generado, tal como se muestra en el historial.
 *
 * @param uri URI del PDF generado como texto (dominio sin dependencias de Android).
 */
data class PdfJob(
    val id: String,
    val fileName: String,
    val createdAt: Long,
    val pageCount: Int,
    val uri: String,
    val sizeBytes: Long,
)
