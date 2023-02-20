package com.csd3156.project1.database.time

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_table")
class Time (
    @ColumnInfo(name = "time") val time: Double,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)