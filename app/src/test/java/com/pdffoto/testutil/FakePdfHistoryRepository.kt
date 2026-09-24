package com.pdffoto.testutil

import com.pdffoto.domain.model.PdfJob
import com.pdffoto.domain.repository.PdfHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/** Doble en memoria de [PdfHistoryRepository] para los tests de JVM. */
class FakePdfHistoryRepository : PdfHistoryRepository {

    val added = mutableListOf<PdfJob>()
    val deleted = mutableListOf<String>()

    private val items = MutableStateFlow<List<PdfJob>>(emptyList())

    fun emit(items: List<PdfJob>) {
        this.items.value = items
    }

    override fun observeAll(): Flow<List<PdfJob>> = items

    override suspend fun add(job: PdfJob) {
        added += job
    }

    override suspend fun delete(id: String) {
        deleted += id
    }
}
