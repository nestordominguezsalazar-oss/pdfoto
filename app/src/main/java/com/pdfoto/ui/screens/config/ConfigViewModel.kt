package com.pdfoto.ui.screens.config

import androidx.lifecycle.ViewModel
import com.pdfoto.data.session.CreationConfig
import com.pdfoto.data.session.CreationSession
import com.pdfoto.domain.model.MarginSize
import com.pdfoto.domain.model.Orientation
import com.pdfoto.domain.model.PageSize
import com.pdfoto.domain.model.Quality
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val session: CreationSession,
) : ViewModel() {

    val uiState: StateFlow<CreationConfig> = session.config

    fun onFileNameChange(value: String) = session.setFileName(value)

    fun onPageSizeSelect(value: PageSize) = session.setPageSize(value)

    fun onOrientationSelect(value: Orientation) = session.setOrientation(value)

    fun onMarginSelect(value: MarginSize) = session.setMargin(value)

    fun onQualitySelect(value: Quality) = session.setQuality(value)
}
