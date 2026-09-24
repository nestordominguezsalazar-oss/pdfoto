# 07 - Almacenamiento y compartir

## Guardar en Downloads (Android 10+, API 29+)
Implementado en `data/storage/AndroidPdfStorage`:

```kotlin
val values = ContentValues().apply {
    put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    put(MediaStore.MediaColumns.IS_PENDING, 1)
}
val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values) ?: error(...)
resolver.openOutputStream(uri)?.use { out -> source.inputStream().use { it.copyTo(out) } }
values.clear(); values.put(MediaStore.MediaColumns.IS_PENDING, 0)
resolver.update(uri, values, null, null)
```

## Android < 10 (API 24-28)
Se guarda en el directorio externo de la app (`getExternalFilesDir(DIRECTORY_DOWNLOADS)`) y se
expone con **FileProvider** (autoridad `${applicationId}.fileprovider`). Así **no se pide**
`WRITE_EXTERNAL_STORAGE`.

## Compartir
`ui/util/PdfIntents.sharePdf` usa `ACTION_SEND` con `application/pdf` y un chooser, concediendo
`FLAG_GRANT_READ_URI_PERMISSION`. `openPdf` usa `ACTION_VIEW`.
