package com.pdffoto.data.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.core.net.toUri
import com.pdffoto.domain.pdf.calculateInSampleSize

/**
 * Decodifica la imagen de [uri] con sampling (para no cargar bitmaps full-res) y la
 * rota [rotationDegrees] si es necesario.
 */
fun decodeSampledBitmap(
    context: Context,
    uri: String,
    maxDimension: Int,
    rotationDegrees: Int,
): Bitmap? {
    val parsedUri = uri.toUri()

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
    } ?: return null

    return if (rotationDegrees != 0) {
        val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            .also { rotated -> if (rotated != bitmap) bitmap.recycle() }
    } else {
        bitmap
    }
}
