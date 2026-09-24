package com.pdffoto.ui.navigation

/**
 * Rutas de navegación de la app.
 *
 * Se usan `String` como rutas (sin argumentos por ahora). Cuando una pantalla necesite
 * argumentos (p. ej. el id de un PDF en el historial) se añadirán aquí.
 */
sealed interface Destination {
    val route: String

    data object Home : Destination {
        override val route: String = "home"
    }

    data object Editor : Destination {
        override val route: String = "editor"
    }

    data object Config : Destination {
        override val route: String = "config"
    }

    data object Generating : Destination {
        override val route: String = "generating"
    }

    data object Result : Destination {
        override val route: String = "result"
    }

    data object History : Destination {
        override val route: String = "history"
    }

    data object Camera : Destination {
        override val route: String = "camera"
    }

    data object About : Destination {
        override val route: String = "about"
    }
}
