package com.pdffoto.data.local

import com.google.common.truth.Truth.assertThat
import com.pdffoto.domain.model.PdfJob
import org.junit.Test

class PdfHistoryMapperTest {

    private val entity = PdfHistoryEntity(
        id = "id-1",
        fileName = "documento.pdf",
        createdAt = 1_700_000_000_000L,
        pageCount = 3,
        uriString = "content://downloads/documento.pdf",
        sizeBytes = 123_456L,
    )

    private val job = PdfJob(
        id = "id-1",
        fileName = "documento.pdf",
        createdAt = 1_700_000_000_000L,
        pageCount = 3,
        uri = "content://downloads/documento.pdf",
        sizeBytes = 123_456L,
    )

    @Test
    fun `entity a dominio conserva todos los campos`() {
        assertThat(entity.toDomain()).isEqualTo(job)
    }

    @Test
    fun `dominio a entity conserva todos los campos`() {
        assertThat(job.toEntity()).isEqualTo(entity)
    }

    @Test
    fun `la conversion es reversible`() {
        assertThat(entity.toDomain().toEntity()).isEqualTo(entity)
    }
}
