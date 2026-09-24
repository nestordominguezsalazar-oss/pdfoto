package com.pdffoto.data.local

import com.pdffoto.domain.model.PdfJob

fun PdfHistoryEntity.toDomain(): PdfJob = PdfJob(
    id = id,
    fileName = fileName,
    createdAt = createdAt,
    pageCount = pageCount,
    uri = uriString,
    sizeBytes = sizeBytes,
)

fun PdfJob.toEntity(): PdfHistoryEntity = PdfHistoryEntity(
    id = id,
    fileName = fileName,
    createdAt = createdAt,
    pageCount = pageCount,
    uriString = uri,
    sizeBytes = sizeBytes,
)
