package com.pdffoto.ui.screens.generating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdffoto.data.pdf.PdfGeneratorService
import com.pdffoto.data.pdf.PdfOutputFileProvider
import com.pdffoto.data.session.CreationSession
import com.pdffoto.data.storage.PdfStorage
import com.pdffoto.domain.model.PdfJob
import com.pdffoto.domain.repository.PdfHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GenerationViewModel @Inject constructor(
    private val session: CreationSession,
    private val pdfGenerator: PdfGeneratorService,
    private val outputFiles: PdfOutputFileProvider,
    private val storage: PdfStorage,
    private val historyRepository: PdfHistoryRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<GenerationUiState>(GenerationUiState.Idle)
    val state: StateFlow<GenerationUiState> = _state.asStateFlow()

    private var generationJob: Job? = null

    /** Genera el PDF desde la sesión y lo guarda en Downloads. */
    fun generate() {
        if (generationJob?.isActive == true) return

        val photos = session.photos.value
        val config = session.config.value
        if (photos.isEmpty()) {
            _state.value = GenerationUiState.Error(message = null)
            return
        }

        generationJob = viewModelScope.launch {
            _state.value = GenerationUiState.InProgress(current = 0, total = photos.size)

            val outputFile = outputFiles.forName(config.fileName)
            pdfGenerator.generate(
                photos = photos,
                config = config.pdfConfig,
                outputFile = outputFile,
                onProgress = { current, total ->
                    _state.value = GenerationUiState.InProgress(current = current, total = total)
                },
            ).fold(
                onSuccess = { _state.value = save(outputFile, config.fileName, photos.size) },
                onFailure = { _state.value = GenerationUiState.Error(message = it.message) },
            )
        }
    }

    private suspend fun save(outputFile: File, fileName: String, pageCount: Int): GenerationUiState =
        storage.saveToDownloads(outputFile, fileName).fold(
            onSuccess = { saved ->
                val job = PdfJob(
                    id = UUID.randomUUID().toString(),
                    fileName = saved.displayName,
                    createdAt = System.currentTimeMillis(),
                    pageCount = pageCount,
                    uri = saved.uri,
                    sizeBytes = saved.sizeBytes,
                )
                historyRepository.add(job)
                session.setLastResult(job)
                GenerationUiState.Success(pdf = job)
            },
            onFailure = { GenerationUiState.Error(message = it.message) },
        )

    /** Cancela la generación en curso y vuelve a [GenerationUiState.Idle]. */
    fun cancel() {
        generationJob?.cancel()
        generationJob = null
        _state.value = GenerationUiState.Idle
    }
}
