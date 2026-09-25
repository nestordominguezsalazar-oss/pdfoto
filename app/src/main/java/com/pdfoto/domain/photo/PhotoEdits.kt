package com.pdfoto.domain.photo

import com.pdfoto.domain.model.Photo

private const val RotationStepDegrees = 90

/**
 * Rota la foto 90° en sentido horario (0 → 90 → 180 → 270 → 0).
 * Función pura para poder probarla en la JVM.
 */
internal fun rotatePhoto(photo: Photo): Photo =
    photo.copy(rotationDegrees = (photo.rotationDegrees + RotationStepDegrees) % 360)

/** Elimina la foto [id] y renumera el orden del resto para que sea consecutivo. */
internal fun removePhoto(photos: List<Photo>, id: String): List<Photo> =
    photos.filterNot { it.id == id }
        .mapIndexed { index, photo -> photo.copy(order = index) }

/**
 * Mueve la foto de [fromIndex] a [toIndex] y renumera el orden.
 * Si los índices no son válidos o son iguales, devuelve la lista sin cambios.
 */
internal fun movePhoto(photos: List<Photo>, fromIndex: Int, toIndex: Int): List<Photo> {
    if (fromIndex == toIndex) return photos
    if (fromIndex !in photos.indices || toIndex !in photos.indices) return photos

    val mutable = photos.toMutableList()
    val moved = mutable.removeAt(fromIndex)
    mutable.add(toIndex, moved)
    return mutable.mapIndexed { index, photo -> photo.copy(order = index) }
}
