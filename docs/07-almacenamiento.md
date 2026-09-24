
---

## 📄 `docs/07-almacenamiento.md`

```markdown
# 07 - Almacenamiento y compartir

## Guardar en Downloads (Android 10+, API 29+)

```kotlin
fun savePdfToDownloads(
    context: Context,
    sourceFile: File,
    displayName: String
): Uri? {
    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        put(MediaStore.MediaColumns.IS_PENDING, 1)
    }
    val resolver = context.contentResolver
    val collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI
    val uri = resolver.insert(collection, values) ?: return null

    resolver.openOutputStream(uri)?.use { out ->
        sourceFile.inputStream().use { it.copyTo(out) }
    }

    values.clear()
    values.put(MediaStore.MediaColumns.IS_PENDING, 0)
    resolver.update(uri, values, null, null)
    return uri
}