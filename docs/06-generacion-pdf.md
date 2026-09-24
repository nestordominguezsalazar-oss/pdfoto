# 06 - Generación del PDF

## Estrategia
Usar `android.graphics.pdf.PdfDocument` nativo (sin librerías externas).

## Tamaño de página (puntos, 1 pt = 1/72 in)
- A4: 595 x 842
- Carta: 612 x 792
- AUTO: dimensiones del bitmap

La orientación (vertical / horizontal) ajusta el tamaño base; AUTO la deduce del bitmap.
Implementado en `domain/pdf/PdfConfigResolver.resolve`.

## Peso del archivo
`PdfDocument` (backend PDF de Skia) incrusta las imágenes **a la resolución del bitmap** y su
codificación por defecto es **sin pérdida**. Por eso el peso depende casi por completo de los
**píxeles** incrustados, no de una "calidad JPEG" (que Skia vuelve a codificar).

Por eso la **calidad se expresa como DPI objetivo** y fija el lado mayor al que se decodifica:

| Calidad | DPI | Alto en A4 |
|---------|-----|------------|
| Baja    | 96  | ~1123 px |
| Media   | 150 | ~1754 px |
| Alta    | 200 | ~2339 px |

`PdfConfigResolver.targetDecodeDimension(pageSize, quality)` calcula el objetivo. El bitmap se
decodifica con sampling (`calculateInSampleSize`) y se ajusta al objetivo con
`Bitmap.scaledDownTo`.

> Nota: los valores de DPI son un punto de partida razonable; conviene **medir en dispositivo**
> el peso resultante y ajustarlos si hace falta.

## Pipeline

`data/pdf/PdfGenerator` (implementa `PdfGeneratorService`):

1. `decodeSampledBitmap(context, uri, targetDimension, rotationDegrees)` → bitmap muestreado y
   **enderezado**: aplica la **orientación EXIF** de la foto (con `androidx.exifinterface`) y
   la rotación pedida por el usuario. Sin esto, las fotos de cámara salen giradas 90°.
2. `bitmap.scaledDownTo(targetDimension)` → tamaño exacto.
3. `PdfConfigResolver.resolve(config, bitmap.width, bitmap.height)` → tamaño de página.
4. `drawBitmapFitted(canvas, bitmap, pageW, pageH, marginPx)` → letterbox centrado + margen.
5. `document.writeTo(outputFile)`.

Se ejecuta en `Dispatchers.IO` y reporta progreso 1-based. `GenerationViewModel` lo orquesta
(con cancelación) y `AndroidPdfStorage` guarda el resultado en Downloads.
