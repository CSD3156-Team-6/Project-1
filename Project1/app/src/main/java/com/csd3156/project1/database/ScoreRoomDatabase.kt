package com.csd3156.project1.database

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.csd3156.project1.database.height.Height
import com.csd3156.project1.database.height.HeightDao
import com.csd3156.project1.database.time.Time
import com.csd3156.project1.database.time.TimeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Database(entities = [Height::class, Time::class], version = 1, exportSchema = false)
public abstract class ScoreRoomDatabase: RoomDatabase() {
    abstract fun heightDao(): HeightDao
    abstract fun timeDao(): TimeDao

    private class ScoreDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ScoreRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ScoreRoomDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreRoomDatabase::class.java,
                    "score_database"
                )
                    .addCallback(ScoreDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}