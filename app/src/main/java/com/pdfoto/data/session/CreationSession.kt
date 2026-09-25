package com.pdfoto.data.session

import com.pdfoto.domain.model.MarginSize
import com.pdfoto.domain.model.Orientation
import com.pdfoto.domain.model.PageSize
import com.pdfoto.domain.model.PdfJob
import com.pdfoto.domain.model.Photo
import com.pdfoto.domain.model.Quality
import com.pdfoto.domain.pdf.defaultPdfFileName
import com.pdfoto.domain.photo.buildPhotos
import com.pdfoto.domain.photo.movePhoto
import com.pdfoto.domain.photo.removePhoto
import com.pdfoto.domain.photo.rotatePhoto
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Estado compartido del flujo "crear PDF": fotos seleccionadas y configuración.
 *
 * Es un singleton de Hilt para que [EditorViewModel], [ConfigViewModel] y
 * [GenerationViewModel] trabajen sobre los mismos datos sin pasar listas por las
 * rutas de navegación. Es Kotlin puro (sin Android), así que se testea en la JVM.
 */
@Singleton
class CreationSession @Inject constructor() {

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    private val _config = MutableStateFlow(newDefaultConfig())
    val config: StateFlow<CreationConfig> = _config.asStateFlow()

    private val _lastResult = MutableStateFlow<PdfJob?>(null)
    val lastResult: StateFlow<PdfJob?> = _lastResult.asStateFlow()

    /** Reinicia el flujo para empezar un PDF nuevo. */
    fun clear() {
        _photos.value = emptyList()
        _config.value = newDefaultConfig()
        _lastResult.value = null
    }

    /** Guarda el último PDF generado (lo muestra la pantalla de resultado). */
    fun setLastResult(job: PdfJob) {
        _lastResult.value = job
    }

    fun addPhotos(uris: List<String>) {
        if (uris.isEmpty()) return
        _photos.update { buildPhotos(it, uris) }
    }

    fun rotate(id: String) {
        _photos.update { list -> list.map { if (it.id == id) rotatePhoto(it) else it } }
    }

    fun delete(id: String) {
        _photos.update { list -> removePhoto(list, id) }
    }

    fun move(fromIndex: Int, toIndex: Int) {
        _photos.update { list -> movePhoto(list, fromIndex, toIndex) }
    }

    fun setFileName(value: String) {
        _config.update { it.copy(fileName = value) }
    }

    fun setPageSize(value: PageSize) {
        _config.update { it.copy(pdfConfig = it.pdfConfig.copy(pageSize = value)) }
    }

    fun setOrientation(value: Orientation) {
        _config.update { it.copy(pdfConfig = it.pdfConfig.copy(orientation = value)) }
    }

    fun setMargin(value: MarginSize) {
        _config.update { it.copy(pdfConfig = it.pdfConfig.copy(margin = value)) }
    }

    fun setQuality(value: Quality) {
        _config.update { it.copy(pdfConfig = it.pdfConfig.copy(quality = value)) }
    }

    private fun newDefaultConfig() = CreationConfig(
        fileName = defaultPdfFileName(System.currentTimeMillis()),
    )
}
