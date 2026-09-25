package com.pdfoto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdfoto.ui.screens.about.AboutScreen
import com.pdfoto.ui.screens.camera.CameraScreen
import com.pdfoto.ui.screens.config.ConfigScreen
import com.pdfoto.ui.screens.editor.EditorScreen
import com.pdfoto.ui.screens.generating.GeneratingScreen
import com.pdfoto.ui.screens.history.HistoryScreen
import com.pdfoto.ui.screens.home.HomeScreen
import com.pdfoto.ui.screens.result.ResultScreen

/**
 * Grafo de navegación de PDFoto.
 *
 * Las pantallas son marcadores de posición hasta que se implementen en sus pasos
 * (ver el orden de implementación en `AGENTS.md`).
 */
@Composable
fun PdfotoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier = modifier,
    ) {
        composable(Destination.Home.route) {
            HomeScreen(
                onCreatePdf = { navController.navigate(Destination.Editor.route) },
                onOpenCamera = { navController.navigate(Destination.Camera.route) },
                onOpenHistory = { navController.navigate(Destination.History.route) },
                onOpenAbout = { navController.navigate(Destination.About.route) },
            )
        }

        composable(Destination.Editor.route) {
            EditorScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(Destination.Config.route) },
                onAddFromCamera = { navController.navigate(Destination.Camera.route) },
            )
        }

        composable(Destination.Config.route) {
            ConfigScreen(
                onBack = { navController.popBackStack() },
                onGenerate = { navController.navigate(Destination.Generating.route) },
            )
        }

        composable(Destination.Generating.route) {
            GeneratingScreen(
                onCancel = { navController.popBackStack() },
                onFinished = {
                    navController.navigate(Destination.Result.route) {
                        popUpTo(Destination.Home.route)
                    }
                },
            )
        }

        composable(Destination.Result.route) {
            ResultScreen(
                onBackHome = {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Home.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Destination.History.route) {
            HistoryScreen(onBack = { navController.popBackStack() })
        }

        composable(Destination.Camera.route) {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onPhotoCaptured = {
                    navController.navigate(Destination.Editor.route) {
                        popUpTo(Destination.Home.route)
                    }
                },
            )
        }

        composable(Destination.About.route) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
