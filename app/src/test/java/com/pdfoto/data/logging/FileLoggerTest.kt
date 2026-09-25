package com.pdfoto.data.logging

import com.google.common.truth.Truth.assertThat
import java.io.File
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class FileLoggerTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `guarda las líneas escritas`() {
        val logger = FileLogger(File(temporaryFolder.root, "pdfoto.log"), maxLines = 10)

        logger.log("linea 1")
        logger.log("linea 2")

        assertThat(logger.read()).isEqualTo("linea 1\nlinea 2\n")
    }

    @Test
    fun `conserva solo las últimas maxLines`() {
        val logger = FileLogger(File(temporaryFolder.root, "pdfoto.log"), maxLines = 3)

        (1..6).forEach { logger.log("linea $it") }

        val lines = logger.read().lines().filter { it.isNotEmpty() }
        assertThat(lines).containsExactly("linea 4", "linea 5", "linea 6").inOrder()
    }

    @Test
    fun `read devuelve vacío si no existe`() {
        val logger = FileLogger(File(temporaryFolder.root, "no-existe.log"))

        assertThat(logger.read()).isEmpty()
    }

    @Test
    fun `clear borra el contenido`() {
        val logger = FileLogger(File(temporaryFolder.root, "pdfoto.log"), maxLines = 10)
        logger.log("algo")

        logger.clear()

        assertThat(logger.read()).isEmpty()
    }
}
