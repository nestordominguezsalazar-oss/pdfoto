package com.pdfoto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PdfHistoryEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class PdfotoDatabase : RoomDatabase() {
    abstract fun pdfHistoryDao(): PdfHistoryDao
}
