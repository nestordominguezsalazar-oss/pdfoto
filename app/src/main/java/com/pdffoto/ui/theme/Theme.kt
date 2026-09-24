package com.pdffoto.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Indigo40,
    onPrimary = Color.White,
    primaryContainer = Indigo90,
    onPrimaryContainer = Indigo10,
    secondary = Teal40,
    onSecondary = Color.White,
    secondaryContainer = Teal90,
    onSecondaryContainer = Teal10,
    background = NeutralLight,
    surface = NeutralLight,
    surfaceVariant = NeutralVariantLight,
)

private val DarkColors = darkColorScheme(
    primary = Indigo80,
    onPrimary = Indigo10,
    primaryContainer = Indigo30,
    onPrimaryContainer = Indigo90,
    secondary = Teal80,
    onSecondary = Teal10,
    secondaryContainer = Teal30,
    onSecondaryContainer = Teal90,
    background = NeutralDark,
    surface = NeutralDark,
    surfaceVariant = NeutralVariantDark,
)

/**
 * Tema Material 3 de PDFoto.
 *
 * @param darkTheme sigue el tema claro/oscuro del sistema por defecto.
 * @param dynamicColor usa colores dinámicos en Android 12+ (API 31).
 */
@Composable
fun PDFotoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
