package com.pdfoto.data.pdf

import java.io.File

/** Proporciona el archivo de salida donde [PdfGeneratorService] escribe el PDF. */
fun interface PdfOutputFileProvider {
    fun forName(fileName: String): File
}
