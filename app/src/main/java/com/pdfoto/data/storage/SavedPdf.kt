package com.pdfoto.data.storage

/** Resultado de guardar un PDF: URI (como texto), nombre visible y tamaño. */
data class SavedPdf(
    val uri: String,
    val displayName: String,
    val sizeBytes: Long,
)
