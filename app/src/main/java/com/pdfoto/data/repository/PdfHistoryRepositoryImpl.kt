package com.pdfoto.data.repository

import com.pdfoto.data.local.PdfHistoryDao
import com.pdfoto.data.local.toDomain
import com.pdfoto.data.local.toEntity
import com.pdfoto.domain.model.PdfJob
import com.pdfoto.domain.repository.PdfHistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PdfHistoryRepositoryImpl @Inject constructor(
    private val dao: PdfHistoryDao,
) : PdfHistoryRepository {

    override fun observeAll(): Flow<List<PdfJob>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun add(job: PdfJob) = dao.insert(job.toEntity())

    override suspend fun delete(id: String) = dao.delete(id)
}
