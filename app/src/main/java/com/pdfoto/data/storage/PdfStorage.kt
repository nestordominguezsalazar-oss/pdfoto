package com.pdfoto.data.storage

import java.io.File

/** Guarda un PDF generado en el almacenamiento del dispositivo. */
interface PdfStorage {
    suspend fun saveToDownloads(source: File, displayName: String): Result<SavedPdf>
}
