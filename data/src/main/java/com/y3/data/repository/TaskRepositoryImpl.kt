package com.y3.data.repository

import com.y3.common.Resource
import com.y3.data.local.entitiy.TaskEntity
import com.y3.data.local.TasksDao
import com.y3.data.di.IoDispatcher
import com.y3.data.local.entitiy.toTaskEntity
import com.y3.data.model.TaskDomainModel
import com.y3.data.model.taskDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TaskRepository {

    override suspend fun insertTask(task: TaskDomainModel) {
        withContext(ioDispatcher){
            tasksDao.insertTask(task.toTaskEntity())
        }
    }

    override suspend fun deleteTaskById(taskId: String): Int {
      return  withContext(ioDispatcher){
            tasksDao.deleteTaskById(taskId)
        }
    }

    override fun observeTasks(): Flow<Resource<List<TaskDomainModel>>> {
        return try {
            tasksDao.observeTasks().map { listTask ->
                Resource.Success(listTask.map { taskEntity ->
                    taskEntity.taskDomainModel()
                })
            }
        } catch (t: Throwable) {
            flowOf(Resource.Failure(t))
        }
    }

    override fun observeTaskById(taskId: String): Flow<Resource<TaskDomainModel>> {
        return try {
            tasksDao.observeTaskById(taskId).map {
                Resource.Success(it.taskDomainModel())
            }

        } catch (t: Throwable) {
            flowOf(Resource.Failure(t))
        }
    }
}