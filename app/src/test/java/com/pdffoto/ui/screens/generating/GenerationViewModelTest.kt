package com.pdffoto.ui.screens.generating

import com.google.common.truth.Truth.assertThat
import com.pdffoto.data.logging.AppLogger
import com.pdffoto.data.pdf.PdfGeneratorService
import com.pdffoto.data.pdf.PdfOutputFileProvider
import com.pdffoto.data.session.CreationSession
import com.pdffoto.data.storage.PdfStorage
import com.pdffoto.data.storage.SavedPdf
import com.pdffoto.domain.model.PdfConfig
import com.pdffoto.domain.model.Photo
import com.pdffoto.testutil.FakePdfHistoryRepository
import com.pdffoto.testutil.MainDispatcherRule
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GenerationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val outputFile = File("/tmp/pdffoto-test.pdf")
    private val outputFileProvider = PdfOutputFileProvider { outputFile }
    private val savedPdf = SavedPdf(
        uri = "content://downloads/documento.pdf",
        displayName = "documento.pdf",
        sizeBytes = 1234,
    )

    private fun sessionWithPhotos(): CreationSession =
        CreationSession().apply { addPhotos(listOf("content://foto/1", "content://foto/2")) }

    private fun viewModel(
        session: CreationSession = sessionWithPhotos(),
        generator: Result<Unit> = Result.success(Unit),
        storage: Result<SavedPdf> = Result.success(savedPdf),
        history: FakePdfHistoryRepository = FakePdfHistoryRepository(),
    ) = GenerationViewModel(
        session = session,
        pdfGenerator = FakePdfGeneratorService(generator),
        outputFiles = outputFileProvider,
        storage = FakePdfStorage(storage),
        historyRepository = history,
        logger = AppLogger { File("/tmp/pdffoto-test.log") },
    )

    @Test
    fun `generar guarda el pdf y termina en exito`() = runTest {
        val session = sessionWithPhotos()
        val history = FakePdfHistoryRepository()
        val viewModel = viewModel(session = session, history = history)

        viewModel.generate()

        val state = viewModel.state.value
        assertThat(state).isInstanceOf(GenerationUiState.Success::class.java)
        assertThat((state as GenerationUiState.Success).pdf.uri).isEqualTo(savedPdf.uri)
        assertThat(session.lastResult.value?.uri).isEqualTo(savedPdf.uri)
        assertThat(history.added.map { it.uri }).containsExactly(savedPdf.uri)
    }

    @Test
    fun `informa del error cuando la generacion falla`() = runTest {
        val viewModel = viewModel(generator = Result.failure(IllegalStateException("boom")))

        viewModel.generate()

        assertThat((viewModel.state.value as GenerationUiState.Error).message).isEqualTo("boom")
    }

    @Test
    fun `informa del error cuando el guardado falla`() = runTest {
        val viewModel = viewModel(storage = Result.failure(IllegalStateException("nope")))

        viewModel.generate()

        assertThat((viewModel.state.value as GenerationUiState.Error).message).isEqualTo("nope")
    }

    @Test
    fun `sin fotos genera estado de error`() = runTest {
        val viewModel = viewModel(session = CreationSession())

        viewModel.generate()

        assertThat(viewModel.state.value).isInstanceOf(GenerationUiState.Error::class.java)
    }

    @Test
    fun `cancelar vuelve a Idle`() = runTest {
        val viewModel = viewModel()

        viewModel.cancel()

        assertThat(viewModel.state.value).isEqualTo(GenerationUiState.Idle)
    }
}

private class FakePdfGeneratorService(
    private val result: Result<Unit>,
) : PdfGeneratorService {
    override suspend fun generate(
        photos: List<Photo>,
        config: PdfConfig,
        outputFile: File,
        onProgress: (current: Int, total: Int) -> Unit,
    ): Result<Unit> {
        onProgress(photos.size, photos.size)
        return result
    }
}

private class FakePdfStorage(
    private val result: Result<SavedPdf>,
) : PdfStorage {
    override suspend fun saveToDownloads(source: File, displayName: String): Result<SavedPdf> = result
}
