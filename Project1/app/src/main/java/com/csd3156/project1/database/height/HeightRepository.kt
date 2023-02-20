package com.csd3156.project1.database.height

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class HeightRepository(private val heightDao: HeightDao) {
    val allHeight: Flow<List<Height>> = heightDao.getHeight()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(height: Height) {
        heightDao.insert(height)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() {
        heightDao.clearAll()
    }
}