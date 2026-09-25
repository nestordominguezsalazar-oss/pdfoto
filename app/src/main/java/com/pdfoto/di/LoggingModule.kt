package com.pdfoto.di

import android.content.Context
import com.pdfoto.data.logging.LogFileProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggingModule {

    @Provides
    @Singleton
    fun provideLogFileProvider(@ApplicationContext context: Context): LogFileProvider =
        LogFileProvider { File(context.cacheDir, "logs/pdfoto.log") }
}
