package com.pdfoto.domain.photo

import com.pdfoto.domain.model.Photo
import java.util.UUID

/**
 * Añade las fotos [uris] a continuación de [existing], asignando un id único y un
 * orden consecutivo. Función pura para poder probarla en la JVM.
 */
internal fun buildPhotos(
    existing: List<Photo>,
    uris: List<String>,
    generateId: () -> String = { UUID.randomUUID().toString() },
): List<Photo> = existing + uris.mapIndexed { index, uri ->
    Photo(
        id = generateId(),
        uri = uri,
        rotationDegrees = 0,
        order = existing.size + index,
    )
}
