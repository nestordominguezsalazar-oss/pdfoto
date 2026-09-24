fun decodeSampledBitmap(
    context: Context,
    uri: Uri,
    maxDim: Int,
    rotation: Int
): Bitmap? {
    val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    context.contentResolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it, null, opts)
    }
    opts.inSampleSize = calculateInSampleSize(opts, maxDim, maxDim)
    opts.inJustDecodeBounds = false

    val bmp = context.contentResolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it, null, opts)
    } ?: return null

    return if (rotation != 0) {
        val m = Matrix().apply { postRotate(rotation.toFloat()) }
        Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, m, true)
            .also { if (it != bmp) bmp.recycle() }
    } else bmp
}