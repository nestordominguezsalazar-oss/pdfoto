package com.pdffoto.di

import android.content.Context
import com.pdffoto.data.pdf.PdfOutputFileProvider
import com.pdffoto.domain.pdf.FileNameSanitizer
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
