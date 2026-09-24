package com.pdffoto.domain.photo

/** Transformación necesaria para enderezar una imagen según su orientación EXIF. */
data class ImageOrientation(
    val rotationDegrees: Int,
    val flipHorizontal: Boolean,
)

/**
 * Convierte el valor de orientación EXIF (1-8) en la rotación y el espejo necesarios.
 *
 * 1 = normal · 3 = 180° · 6 = 90° horario · 8 = 270° horario · 2/4/5/7 incluyen espejo.
 * Cualquier valor desconocido se trata como "sin cambios".
 */
fun exifOrientationToTransform(exifOrientation: Int): ImageOrientation = when (exifOrientation) {
    2 -> ImageOrientation(rotationDegrees = 0, flipHorizontal = true)
    3 -> ImageOrientation(rotationDegrees = 180, flipHorizontal = false)
    4 -> ImageOrientation(rotationDegrees = 180, flipHorizontal = true)
    5 -> ImageOrientation(rotationDegrees = 90, flipHorizontal = true)
    6 -> ImageOrientation(rotationDegrees = 90, flipHorizontal = false)
    7 -> ImageOrientation(rotationDegrees = 270, flipHorizontal = true)
    8 -> ImageOrientation(rotationDegrees = 270, flipHorizontal = false)
    else -> ImageOrientation(rotationDegrees = 0, flipHorizontal = false)
}
