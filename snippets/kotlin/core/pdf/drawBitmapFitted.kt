fun drawBitmapFitted(
    canvas: Canvas, bitmap: Bitmap,
    pageW: Int, pageH: Int, marginPx: Int
) {
    val availW = pageW - marginPx * 2
    val availH = pageH - marginPx * 2
    val scale = minOf(availW.toFloat() / bitmap.width, availH.toFloat() / bitmap.height)
    val w = bitmap.width * scale
    val h = bitmap.height * scale
    val left = (pageW - w) / 2f
    val top = (pageH - h) / 2f
    val dst = RectF(left, top, left + w, top + h)
    canvas.drawBitmap(bitmap, null, dst, Paint(Paint.FILTER_BITMAP_FLAG))
}