package com.pdffoto.di

import android.content.Context
import androidx.room.Room
import com.pdffoto.data.local.PdfHistoryDao
import com.pdffoto.data.local.PdfotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PdfotoDatabase =
        Room.databaseBuilder(context, PdfotoDatabase::class.java, "pdffoto.db").build()

    @Provides
    fun providePdfHistoryDao(database: PdfotoDatabase): PdfHistoryDao = database.pdfHistoryDao()
}
