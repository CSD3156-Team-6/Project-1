package com.csd3156.project1.database.time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM time_table")
    fun getTime(): Flow<List<Timer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timer: Timer)

    @Query("DELETE FROM time_table")
    suspend fun clearAll()
}