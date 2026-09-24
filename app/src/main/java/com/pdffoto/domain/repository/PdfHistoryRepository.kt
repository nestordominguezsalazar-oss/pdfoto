package com.pdffoto.domain.repository

import com.pdffoto.domain.model.PdfJob
import kotlinx.coroutines.flow.Flow

/**
 * Acceso al historial de PDFs generados.
 */
interface PdfHistoryRepository {

    /** Emite el historial completo, ordenado de más reciente a más antiguo. */
    fun observeAll(): Flow<List<PdfJob>>

    /** Añade (o reemplaza) una entrada del historial. */
    suspend fun add(job: PdfJob)

    /** Elimina una entrada por su [id]. */
    suspend fun delete(id: String)
}
