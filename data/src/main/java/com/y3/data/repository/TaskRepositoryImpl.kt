package com.y3.data.repository

import com.y3.data.local.TasksDao
import com.y3.data.di.IoDispatcher
import com.y3.data.local.entitiy.TaskEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TaskRepository {

    override suspend fun insertTask(taskEntity: TaskEntity) {
        withContext(ioDispatcher){
            tasksDao.insertTask(taskEntity)
        }
    }

    override suspend fun deleteTaskById(taskId: String): Int {
      return  withContext(ioDispatcher){
            tasksDao.deleteTaskById(taskId)
        }
    }

    override fun observeTasks() = tasksDao.observeTasks()

    override fun observeTaskById(taskId: String) = tasksDao.observeTaskById(taskId)
}