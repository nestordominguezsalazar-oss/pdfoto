package com.pdfoto.di

import android.content.Context
import com.pdfoto.data.pdf.PdfOutputFileProvider
import com.pdfoto.domain.pdf.FileNameSanitizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PdfModule {

    @Provides
    @Singleton
    fun providePdfOutputFileProvider(
        @ApplicationContext context: Context,
    ): PdfOutputFileProvider = PdfOutputFileProvider { fileName ->
        File(context.cacheDir, FileNameSanitizer.sanitize(fileName))
    }
}
