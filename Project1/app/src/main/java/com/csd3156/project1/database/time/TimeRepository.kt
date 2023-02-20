package com.csd3156.project1.database.time

import androidx.annotation.WorkerThread
import com.csd3156.project1.database.height.Height
import com.csd3156.project1.database.height.HeightDao
import kotlinx.coroutines.flow.Flow

class TimeRepository(private val timeDao: TimeDao) {
    val allTime: Flow<List<Time>> = timeDao.getTime()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(time: Time) {
        timeDao.insert(time)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() {
        timeDao.clearAll()
    }
}