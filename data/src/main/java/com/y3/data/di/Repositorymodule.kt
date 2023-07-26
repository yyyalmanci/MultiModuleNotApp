package com.y3.data.di

import com.y3.data.repository.TaskRepository
import com.y3.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideTaskRepositoryImpl(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

}