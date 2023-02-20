package com.csd3156.project1.database.time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {
    @Query("SELECT * FROM time_table")
    fun getTime(): Flow<List<Time>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(time: Time)

    @Query("DELETE FROM time_table")
    suspend fun clearAll()
}