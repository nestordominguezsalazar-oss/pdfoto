package com.pdffoto.data.repository

import com.pdffoto.data.local.PdfHistoryDao
import com.pdffoto.data.local.toDomain
import com.pdffoto.data.local.toEntity
import com.pdffoto.domain.model.PdfJob
import com.pdffoto.domain.repository.PdfHistoryRepository
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
