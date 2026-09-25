package com.pdfoto.ui.screens.editor

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pdfoto.R
import com.pdfoto.data.session.CreationSession
import com.pdfoto.ui.theme.PDFotoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Smoke test de UI de [EditorScreen]: comprueba que la pantalla se renderiza y que la
 * acción principal está disponible cuando hay una foto.
 */
@RunWith(AndroidJUnit4::class)
class EditorScreenSmokeTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun muestraElBotonContinuarCuandoHayUnaFoto() {
        // Con una foto en la sesión no se lanza el Photo Picker al entrar.
        val session = CreationSession().apply { addPhotos(listOf("content://pdfoto/foto/1")) }
        val textoContinuar = context.getString(R.string.action_continue)

        composeRule.setContent {
            PDFotoTheme {
                EditorScreen(
                    onBack = {},
                    onContinue = {},
                    onAddFromCamera = {},
                    viewModel = EditorViewModel(session),
                )
            }
        }

        composeRule.onNodeWithText(textoContinuar).assertIsDisplayed()
    }
}
