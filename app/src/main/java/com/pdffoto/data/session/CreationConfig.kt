package com.pdffoto.data.session

import com.pdffoto.domain.model.PdfConfig

/** Configuración elegida en la pantalla de configuración (nombre + opciones del PDF). */
data class CreationConfig(
    val fileName: String,
    val pdfConfig: PdfConfig = PdfConfig(),
)
