package com.pdffoto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdffoto.ui.screens.camera.CameraScreen
import com.pdffoto.ui.screens.config.ConfigScreen
import com.pdffoto.ui.screens.editor.EditorScreen
import com.pdffoto.ui.screens.generating.GeneratingScreen
import com.pdffoto.ui.screens.history.HistoryScreen
import com.pdffoto.ui.screens.home.HomeScreen
import com.pdffoto.ui.screens.result.ResultScreen

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
            )
        }

        composable(Destination.Editor.route) {
            EditorScreen(
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(Destination.Config.route) },
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
    }
}
