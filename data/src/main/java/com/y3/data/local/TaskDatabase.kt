package com.y3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.y3.data.local.entitiy.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao

}