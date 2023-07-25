package com.y3.data.local.repository

import com.y3.data.local.Resource
import com.y3.data.local.TaskEntity
import com.y3.data.local.TasksDao
import com.y3.data.local.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TaskRepository {

    override suspend fun insertTask(task: TaskEntity) {
        tasksDao.insertTask(task)
    }

    override suspend fun deleteTaskById(taskId: String): Int {
        return tasksDao.deleteTaskById(taskId)
    }

    override fun observeTasks(): Flow<Resource<List<TaskEntity>>> {
        return try {
            tasksDao.observeTasks().map {
                Resource.Success(it)
            }
        } catch (t: Throwable) {
            flowOf(Resource.Failure(t))
        }
    }

    override fun observeTaskById(taskId: String): Flow<Resource<TaskEntity>> {
        return try {
            tasksDao.observeTaskById(taskId).map {
                Resource.Success(it)
            }

        } catch (t: Throwable) {
            flowOf(Resource.Failure(t))
        }
    }
}