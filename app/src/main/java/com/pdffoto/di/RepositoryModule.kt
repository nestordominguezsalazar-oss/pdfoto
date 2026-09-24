package com.pdffoto.di

import com.pdffoto.data.repository.PdfHistoryRepositoryImpl
import com.pdffoto.domain.repository.PdfHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPdfHistoryRepository(impl: PdfHistoryRepositoryImpl): PdfHistoryRepository
}
