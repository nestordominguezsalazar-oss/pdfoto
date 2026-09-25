package com.pdfoto.ui.screens.config

import com.google.common.truth.Truth.assertThat
import com.pdfoto.data.session.CreationSession
import com.pdfoto.domain.model.MarginSize
import com.pdfoto.domain.model.Orientation
import com.pdfoto.domain.model.PageSize
import com.pdfoto.domain.model.Quality
import org.junit.Test

class ConfigViewModelTest {

    private fun viewModel() = ConfigViewModel(CreationSession())

    @Test
    fun `el nombre por defecto sigue el patron pdfoto_fecha`() {
        assertThat(viewModel().uiState.value.fileName).matches("pdfoto_\\d{8}_\\d{4}")
    }

    @Test
    fun `la configuracion por defecto es AUTO, sin margen y calidad media`() {
        val config = viewModel().uiState.value.pdfConfig

        assertThat(config.pageSize).isEqualTo(PageSize.AUTO)
        assertThat(config.orientation).isEqualTo(Orientation.AUTO)
        assertThat(config.margin).isEqualTo(MarginSize.NONE)
        assertThat(config.quality).isEqualTo(Quality.MEDIUM)
    }

    @Test
    fun `cambiar el tamano de pagina actualiza el estado`() {
        val viewModel = viewModel()

        viewModel.onPageSizeSelect(PageSize.A4)

        assertThat(viewModel.uiState.value.pdfConfig.pageSize).isEqualTo(PageSize.A4)
    }

    @Test
    fun `cambiar la orientacion actualiza el estado`() {
        val viewModel = viewModel()

        viewModel.onOrientationSelect(Orientation.LANDSCAPE)

        assertThat(viewModel.uiState.value.pdfConfig.orientation).isEqualTo(Orientation.LANDSCAPE)
    }

    @Test
    fun `cambiar el nombre del archivo actualiza el estado`() {
        val viewModel = viewModel()

        viewModel.onFileNameChange("mis_apuntes")

        assertThat(viewModel.uiState.value.fileName).isEqualTo("mis_apuntes")
    }
}
