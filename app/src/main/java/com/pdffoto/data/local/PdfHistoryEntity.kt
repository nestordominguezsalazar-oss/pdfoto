package com.pdffoto.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Fila de la tabla `pdf_history` en Room.
 */
@Entity(tableName = "pdf_history")
data class PdfHistoryEntity(
    @PrimaryKey val id: String,
    val fileName: String,
    val createdAt: Long,
    val pageCount: Int,
    val uriString: String,
    val sizeBytes: Long,
)
