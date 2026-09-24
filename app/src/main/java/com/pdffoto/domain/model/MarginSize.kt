package com.pdffoto.domain.model

/**
 * Margen de la página.
 *
 * @param dp margen en dp para [NONE], [SMALL] y [LARGE].
 */
enum class MarginSize(val dp: Int) {
    NONE(0),
    SMALL(24),
    LARGE(48),
}
