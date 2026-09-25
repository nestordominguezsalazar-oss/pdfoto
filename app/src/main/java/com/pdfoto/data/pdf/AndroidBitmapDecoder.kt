package com.pdfoto.data.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.pdfoto.domain.pdf.calculateInSampleSize
import com.pdfoto.domain.photo.ImageOrientation
import com.pdfoto.domain.photo.exifOrientationToTransform

/**
 * Decodifica la imagen de [uri] con sampling (para no cargar bitmaps full-res) y la
 * endereza aplicando su **orientación EXIF** más la rotación pedida por el usuario.
 *
 * Devuelve `null` si la imagen no se puede **abrir ni decodificar** (URI inaccesible,
 * permiso revocado o archivo corrupto), de modo que una foto problemática se omita y no
 * aborte la generación completa.
 */
fun decodeSampledBitmap(
    context: Context,
    uri: String,
    maxDimension: Int,
    rotationDegrees: Int,
): Bitmap? = runCatching {
    val parsedUri = uri.toUri()
    val exifOrientation = context.readExifOrientation(parsedUri)

    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    context.contentResolver.openInputStream(parsedUri)?.use {
        BitmapFactory.decodeStream(it, null, bounds)
    }

    val options = BitmapFactory.Options().apply {
        inSampleSize = calculateInSampleSize(bounds.outWidth, bounds.outHeight, maxDimension)
        inJustDecodeBounds = false
    }

    val bitmap = context.contentResolver.openInputStream(parsedUri)?.use {
        BitmapFactory.decodeStream(it, null, options)
    } ?: return@runCatching null

    bitmap.oriented(exifOrientation, rotationDegrees)
}.getOrNull()

private fun Context.readExifOrientation(uri: Uri): ImageOrientation {
    val orientation = runCatching {
        contentResolver.openInputStream(uri)?.use { stream ->
            ExifInterface(stream).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL,
            )
        }
    }.getOrNull() ?: ExifInterface.ORIENTATION_NORMAL

    return exifOrientationToTransform(orientation)
}

private fun Bitmap.oriented(exif: ImageOrientation, userRotationDegrees: Int): Bitmap {
    val needsTransform = exif.rotationDegrees != 0 || exif.flipHorizontal || userRotationDegrees != 0
    if (!needsTransform) return this

    val matrix = Matrix().apply {
        setRotate(exif.rotationDegrees.toFloat())
        if (exif.flipHorizontal) postScale(-1f, 1f)
        if (userRotationDegrees != 0) postRotate(userRotationDegrees.toFloat())
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
        .also { if (it !== this) recycle() }
}
