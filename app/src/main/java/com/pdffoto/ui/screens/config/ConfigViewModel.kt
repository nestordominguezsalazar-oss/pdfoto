package com.pdffoto.ui.screens.config

import androidx.lifecycle.ViewModel
import com.pdffoto.data.session.CreationConfig
import com.pdffoto.data.session.CreationSession
import com.pdffoto.domain.model.MarginSize
import com.pdffoto.domain.model.Orientation
import com.pdffoto.domain.model.PageSize
import com.pdffoto.domain.model.Quality
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
