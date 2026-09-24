package com.pdffoto.ui.screens.history

import com.google.common.truth.Truth.assertThat
import com.pdffoto.domain.model.PdfJob
import com.pdffoto.testutil.FakePdfHistoryRepository
import com.pdffoto.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakePdfHistoryRepository()
    private val job = PdfJob(
        id = "id-1",
        fileName = "documento.pdf",
        createdAt = 1_700_000_000_000L,
        pageCount = 2,
        uri = "content://downloads/documento.pdf",
        sizeBytes = 1024,
    )

    @Test
    fun `sin pdfs el estado es Empty`() = runTest {
        val viewModel = HistoryViewModel(repository)

        assertThat(viewModel.uiState.value).isEqualTo(HistoryUiState.Empty)
    }

    @Test
    fun `con pdfs el estado es Content`() = runTest {
        repository.emit(listOf(job))

        val viewModel = HistoryViewModel(repository)

        assertThat((viewModel.uiState.value as HistoryUiState.Content).items).containsExactly(job)
    }

    @Test
    fun `eliminar delega en el repositorio`() = runTest {
        val viewModel = HistoryViewModel(repository)

        viewModel.onDelete("id-1")

        assertThat(repository.deleted).containsExactly("id-1")
    }
}
