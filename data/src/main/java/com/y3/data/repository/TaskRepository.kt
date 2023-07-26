package com.y3.data.repository

import com.y3.common.Resource
import com.y3.data.local.entitiy.TaskEntity
import com.y3.data.model.TaskDomainModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: TaskDomainModel)

    suspend fun deleteTaskById(taskId: String): Int

    fun observeTasks(): Flow<Resource<List<TaskDomainModel>>>

    fun observeTaskById(taskId: String): Flow<Resource<TaskDomainModel>>
}