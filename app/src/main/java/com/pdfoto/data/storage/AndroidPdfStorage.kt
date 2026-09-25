package com.pdfoto.data.storage

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.pdfoto.domain.pdf.FileNameSanitizer
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val PDF_MIME_TYPE = "application/pdf"

/**
 * Guarda el PDF en la carpeta pública **Downloads** usando MediaStore en Android 10+
 * (API 29). En versiones anteriores usa el directorio de la app expuesto por
 * FileProvider (evita pedir permiso de almacenamiento).
 */
class AndroidPdfStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) : PdfStorage {

    override suspend fun saveToDownloads(source: File, displayName: String): Result<SavedPdf> =
        withContext(Dispatchers.IO) {
            runCatching {
                val safeName = FileNameSanitizer.sanitize(displayName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveViaMediaStore(source, safeName)
                } else {
                    saveViaFileProvider(source, safeName)
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveViaMediaStore(source: File, displayName: String): SavedPdf {
        val resolver = context.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, PDF_MIME_TYPE)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        val uri = resolver.insert(collection, values)
            ?: error("No se pudo crear el archivo en Downloads")

        resolver.openOutputStream(uri)?.use { output ->
            source.inputStream().use { input -> input.copyTo(output) }
        } ?: error("No se pudo escribir el PDF")

        values.clear()
        values.put(MediaStore.MediaColumns.IS_PENDING, 0)
        resolver.update(uri, values, null, null)

        return SavedPdf(uri = uri.toString(), displayName = displayName, sizeBytes = source.length())
    }

    private fun saveViaFileProvider(source: File, displayName: String): SavedPdf {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            ?: context.filesDir
        if (!directory.exists()) directory.mkdirs()

        val target = File(directory, displayName)
        source.copyTo(target, overwrite = true)

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            target,
        )
        return SavedPdf(uri = uri.toString(), displayName = displayName, sizeBytes = target.length())
    }
}
