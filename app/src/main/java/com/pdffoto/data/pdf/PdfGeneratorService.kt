package com.pdffoto.data.pdf

import com.pdffoto.domain.model.PdfConfig
import com.pdffoto.domain.model.Photo
import java.io.File

/**
 * Contrato de generación de PDF. Permite sustituirlo por un doble en los tests.
 */
interface PdfGeneratorService {
    suspend fun generate(
        photos: List<Photo>,
        config: PdfConfig,
        outputFile: File,
        onProgress: (current: Int, total: Int) -> Unit = { _, _ -> },
    ): Result<Unit>
}
