package com.pdfoto.di

import com.pdfoto.data.pdf.PdfGenerator
import com.pdfoto.data.pdf.PdfGeneratorService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PdfGeneratorModule {

    @Binds
    @Singleton
    abstract fun bindPdfGeneratorService(impl: PdfGenerator): PdfGeneratorService
}
