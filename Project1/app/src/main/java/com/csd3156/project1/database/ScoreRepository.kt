package com.csd3156.project1.database

import androidx.annotation.WorkerThread
import com.csd3156.project1.database.height.Height
import com.csd3156.project1.database.height.HeightDao
import com.csd3156.project1.database.time.Timer
import com.csd3156.project1.database.time.TimerDao
import kotlinx.coroutines.flow.Flow

class ScoreRepository(
    private val timeDao: TimerDao,
    private val heightDao: HeightDao
) {

    val allHeight: Flow<List<Height>> = heightDao.getHeight()
    val allTime: Flow<List<Timer>> = timeDao.getTime()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertHeight(height: Height) {
        heightDao.insert(height)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTime(time: Timer) {
        timeDao.insert(time)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearHeight() {
        heightDao.clearAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearTime() {
        timeDao.clearAll()
    }

}