package com.pdfoto.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pdfoto.ui.navigation.PdfotoNavHost
import com.pdfoto.ui.theme.PDFotoTheme

/**
 * Raíz de la UI de la app: aplica el tema y monta el grafo de navegación.
 */
@Composable
fun PdfotoRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    PDFotoTheme {
        PdfotoNavHost(
            navController = navController,
            modifier = modifier,
        )
    }
}
