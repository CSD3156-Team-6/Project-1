package com.csd3156.project1.database.height

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HeightDao {
    @Query("SELECT * FROM height_table")
    fun getHeight(): Flow<List<Height>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(height: Height)

    @Query("DELETE FROM height_table")
    suspend fun clearAll()
}