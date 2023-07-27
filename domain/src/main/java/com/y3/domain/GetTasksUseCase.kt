package com.y3.domain

import com.y3.common.Resource
import com.y3.domain.model.TaskDomainModel
import com.y3.data.repository.TaskRepository
import com.y3.domain.model.taskDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<Resource<List<TaskDomainModel>>> =
        try {
            taskRepository.observeTasks().map { listTask ->
                Resource.Success(listTask.map { taskEntity ->
                    taskEntity.taskDomainModel()
                })
            }
        } catch (t: Throwable) {
            flowOf(Resource.Failure(t))
        }

}