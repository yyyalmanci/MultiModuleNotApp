package com.y3.data.local.di

import android.content.Context
import androidx.room.Room
import com.y3.data.local.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TasksDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun provideTaskDao(
        database: TasksDatabase
    ) = database.taskDao()

    private const val DB_NAME = "TaskDb"
}