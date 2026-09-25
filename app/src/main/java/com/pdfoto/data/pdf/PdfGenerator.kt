package com.pdfoto.data.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import com.pdfoto.domain.model.PdfConfig
import com.pdfoto.domain.model.Photo
import com.pdfoto.domain.pdf.PdfConfigResolver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Genera un PDF a partir de una lista de fotos usando `PdfDocument` nativo.
 *
 * La resolución de las imágenes se ajusta al DPI de la calidad elegida
 * ([PdfConfigResolver.targetDecodeDimension]), que es lo que controla el peso del archivo.
 * Se ejecuta en [Dispatchers.IO] e informa del progreso con `onProgress` (1-based).
 */
class PdfGenerator @Inject constructor(
    @ApplicationContext private val context: Context,
) : PdfGeneratorService {

    override suspend fun generate(
        photos: List<Photo>,
        config: PdfConfig,
        outputFile: File,
        onProgress: (current: Int, total: Int) -> Unit,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val document = PdfDocument()
            try {
                val targetDimension = PdfConfigResolver.targetDecodeDimension(config.pageSize, config.quality)

                photos.sortedBy { it.order }.forEachIndexed { index, photo ->
                    onProgress(index + 1, photos.size)

                    val bitmap = decodeSampledBitmap(
                        context = context,
                        uri = photo.uri,
                        maxDimension = targetDimension,
                        rotationDegrees = photo.rotationDegrees,
                    )?.scaledDownTo(targetDimension)
                        ?: return@forEachIndexed

                    val dimensions = PdfConfigResolver.resolve(config, bitmap.width, bitmap.height)
                    val pageInfo = PdfDocument.PageInfo
                        .Builder(dimensions.widthPt, dimensions.heightPt, index + 1)
                        .create()
                    val page = document.startPage(pageInfo)

                    drawBitmapFitted(
                        canvas = page.canvas,
                        bitmap = bitmap,
                        pageWidth = dimensions.widthPt,
                        pageHeight = dimensions.heightPt,
                        marginPx = config.margin.dp,
                    )

                    document.finishPage(page)
                    bitmap.recycle()
                }

                outputFile.outputStream().use { document.writeTo(it) }
            } finally {
                document.close()
            }
        }
    }
}
