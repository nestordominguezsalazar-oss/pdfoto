package com.pdfoto.ui.screens.config

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
 * Smoke test de UI de [ConfigScreen]: comprueba que la pantalla se renderiza y que el botón
 * de generar está presente.
 */
@RunWith(AndroidJUnit4::class)
class ConfigScreenSmokeTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun muestraElBotonGenerarPdf() {
        val session = CreationSession()
        val textoGenerar = context.getString(R.string.action_generate_pdf)

        composeRule.setContent {
            PDFotoTheme {
                ConfigScreen(
                    onBack = {},
                    onGenerate = {},
                    viewModel = ConfigViewModel(session),
                )
            }
        }

        composeRule.onNodeWithText(textoGenerar).assertIsDisplayed()
    }
}
