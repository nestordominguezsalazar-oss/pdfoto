package com.pdffoto.di

import com.pdffoto.data.storage.AndroidPdfStorage
import com.pdffoto.data.storage.PdfStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun bindPdfStorage(impl: AndroidPdfStorage): PdfStorage
}
