package com.csd3156.project1.database.height

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "height_table")
class Height (
    @ColumnInfo(name = "height") val height: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)