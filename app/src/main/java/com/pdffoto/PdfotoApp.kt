package com.pdffoto

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase `Application` de PDFoto. Hilt la usa como contenedor raíz de dependencias.
 */
@HiltAndroidApp
class PdfotoApp : Application()
