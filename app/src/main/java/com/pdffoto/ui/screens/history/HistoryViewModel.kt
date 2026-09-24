package com.pdffoto.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdffoto.domain.repository.PdfHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: PdfHistoryRepository,
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> = repository.observeAll()
        .map { items -> if (items.isEmpty()) HistoryUiState.Empty else HistoryUiState.Content(items) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = HistoryUiState.Empty,
        )

    fun onDelete(id: String) {
        viewModelScope.launch { repository.delete(id) }
    }
}
