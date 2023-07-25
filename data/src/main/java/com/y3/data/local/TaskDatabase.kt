package com.y3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TaskEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao

}