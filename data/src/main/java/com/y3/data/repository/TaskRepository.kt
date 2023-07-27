package com.y3.data.repository

import com.y3.data.local.entitiy.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(taskEntity: TaskEntity)

    suspend fun deleteTaskById(taskId: String): Int

    fun observeTasks(): Flow<List<TaskEntity>>

    fun observeTaskById(taskId: String): Flow<TaskEntity>
}