package com.pdffoto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfHistoryDao {

    @Query("SELECT * FROM pdf_history ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<PdfHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PdfHistoryEntity)

    @Query("DELETE FROM pdf_history WHERE id = :id")
    suspend fun delete(id: String)
}
