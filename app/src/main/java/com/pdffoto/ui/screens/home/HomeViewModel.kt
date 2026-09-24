package com.pdffoto.ui.screens.home

import androidx.lifecycle.ViewModel
import com.pdffoto.data.session.CreationSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val session: CreationSession,
) : ViewModel() {

    /** Empieza un PDF nuevo: limpia fotos y configuración anteriores. */
    fun startNewCreation() = session.clear()
}
