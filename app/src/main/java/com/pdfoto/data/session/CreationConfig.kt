package com.pdfoto.data.session

import com.pdfoto.domain.model.PdfConfig

/** Configuración elegida en la pantalla de configuración (nombre + opciones del PDF). */
data class CreationConfig(
    val fileName: String,
    val pdfConfig: PdfConfig = PdfConfig(),
)
