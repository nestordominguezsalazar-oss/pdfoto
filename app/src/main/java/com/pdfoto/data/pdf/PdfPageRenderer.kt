package com.pdfoto.data.pdf

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * Dibuja el [bitmap] encajado en la página (letterbox), centrado y respetando
 * [marginPx] en los cuatro lados.
 */
fun drawBitmapFitted(
    canvas: Canvas,
    bitmap: Bitmap,
    pageWidth: Int,
    pageHeight: Int,
    marginPx: Int,
) {
    val availableWidth = pageWidth - marginPx * 2
    val availableHeight = pageHeight - marginPx * 2
    val scale = minOf(
        availableWidth.toFloat() / bitmap.width,
        availableHeight.toFloat() / bitmap.height,
    )

    val width = bitmap.width * scale
    val height = bitmap.height * scale
    val left = (pageWidth - width) / 2f
    val top = (pageHeight - height) / 2f
    val destination = RectF(left, top, left + width, top + height)

    canvas.drawBitmap(bitmap, null, destination, Paint(Paint.FILTER_BITMAP_FLAG))
}
