package com.y3.data.local.repository

import com.y3.data.local.Resource
import com.y3.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTaskById(taskId: String): Int

    fun observeTasks(): Flow<Resource<List<TaskEntity>>>

    fun observeTaskById(taskId: String): Flow<Resource<TaskEntity>>
}