
---

## 📄 `docs/06-generacion-pdf.md`

```markdown
# 06 - Generación del PDF

## Estrategia
Usar `android.graphics.pdf.PdfDocument` nativo (sin librerías externas).

## Cálculo de tamaño de página

### Tamaños en puntos (1 pt = 1/72 in)
- A4: 595 x 842
- Carta: 612 x 792

### Modo AUTO
Cada página usa las dimensiones del bitmap (escaladas a 72 dpi como referencia).

### Modo PORTRAIT / LANDSCAPE
Fijar tamaño base y escalar la imagen con `Matrix` para encajar manteniendo aspect ratio (letterbox).

## Pipeline

```kotlin
class PdfGenerator(
    private val context: Context,
    private val imageLoader: ImageLoader // o decoder propio
) {
    suspend fun generate(
        photos: List<Photo>,
        config: PdfConfig,
        outputFile: File,
        onProgress: (Int, Int) -> Unit
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val doc = PdfDocument()
            photos.sortedBy { it.order }.forEachIndexed { idx, photo ->
                onProgress(idx + 1, photos.size)

                val bitmap = decodeSampledBitmap(
                    context, photo.uri,
                    maxDim = config.pageSize.maxDim(),
                    rotation = photo.rotationDegrees
                ) ?: return@forEachIndexed

                val (pageW, pageH) = resolvePageSize(config, bitmap)

                val pageInfo = PdfDocument.PageInfo
                    .Builder(pageW, pageH, idx + 1).create()
                val page = doc.startPage(pageInfo)

                drawBitmapFitted(
                    canvas = page.canvas,
                    bitmap = bitmap,
                    pageW = pageW,
                    pageH = pageH,
                    marginPx = config.margin.dp
                )

                doc.finishPage(page)
                bitmap.recycle()
            }

            outputFile.outputStream().use { doc.writeTo(it) }
            doc.close()
        }
    }
}